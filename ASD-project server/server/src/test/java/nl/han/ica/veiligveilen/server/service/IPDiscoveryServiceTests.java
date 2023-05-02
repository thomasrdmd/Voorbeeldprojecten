package nl.han.ica.veiligveilen.server.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.dao.IAuctionRepository;
import nl.han.ica.veiligveilen.server.dao.IIPDiscoveryRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import nl.han.ica.veiligveilen.server.model.dataclasses.AuctionType;
import nl.han.ica.veiligveilen.server.model.dataclasses.Category;
import nl.han.ica.veiligveilen.server.model.exceptions.NoMasterFoundException;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostIPDiscoveryRequest;
import nl.han.ica.veiligveilen.server.model.dataclasses.IPDiscovery;
import nl.han.ica.veiligveilen.server.model.responseobjects.IPDiscoveryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class IPDiscoveryServiceTests {

	private IIPDiscoveryRepository discoveryRepositoryFixture;

	private IAuctionRepository auctionRepositoryFixture;

	private IPDiscoveryService sut;

	private IPDiscovery iPDiscovery;

	private PostIPDiscoveryRequest request;

	private final UUID mockAuctionID = UUID.randomUUID();

	@BeforeEach
	void setup(){
		discoveryRepositoryFixture = Mockito.mock(IIPDiscoveryRepository.class);
		auctionRepositoryFixture = Mockito.mock(IAuctionRepository.class);

		sut = new IPDiscoveryService(discoveryRepositoryFixture, auctionRepositoryFixture);

		iPDiscovery = new IPDiscovery(0,"mockIP",new Auction(mockAuctionID,
			"name", new Category(UUID.randomUUID(), "category"),
			new AuctionType(UUID.randomUUID(), "type"),
			Date.from(Instant.now()), Date.from(Instant.now())));

		request = new PostIPDiscoveryRequest("mockIP", mockAuctionID);
	}

	@Test
	void testAddNewMasterSuccess(){
		// Arrange
		Mockito.when(auctionRepositoryFixture.findById(request.getAuctionID()))
			.thenReturn(Optional.of(new Auction()));

		List<IPDiscovery> mockList = new ArrayList<>();
		mockList.add(iPDiscovery);

		when(discoveryRepositoryFixture.findAll()).thenReturn(mockList);
		// Act
		sut.addNewMaster(request);

		// Assert
		verify(discoveryRepositoryFixture).save(Mockito.any());
	}

	@Test
	void testGetAll() {
		List<IPDiscovery> mockList = new ArrayList<>();
		mockList.add(iPDiscovery);

		when(discoveryRepositoryFixture.findAll()).thenReturn(mockList);

		var result = sut.getAll();

		Assertions.assertEquals(result, mockList);
	}

	@Test
	void testGetMasterOfAuctionSuccess() {
		Auction auction = new Auction();
		iPDiscovery.setAuction(auction);

		Mockito.when(auctionRepositoryFixture.findById(mockAuctionID))
			.thenReturn(Optional.of(auction));

		when(discoveryRepositoryFixture.findByAuction(iPDiscovery.getAuction()))
			.thenReturn(Optional.of(iPDiscovery));

		IPDiscoveryResponse expected = new IPDiscoveryResponse(iPDiscovery.getDiscoveryId(),
			iPDiscovery.getIp(), iPDiscovery.getAuction().getAuctionId());

		var result = sut.getMasterOfAuction(mockAuctionID);

		Assertions.assertEquals(result, expected);
	}

	@Test
	void testGetMasterOfAuctionFailure() {
		when(auctionRepositoryFixture.findById(mockAuctionID)).thenReturn(Optional.empty());

		when(discoveryRepositoryFixture.findByAuction(iPDiscovery.getAuction()))
			.thenReturn(Optional.empty());

		Assertions.assertThrows(NoMasterFoundException.class, () ->
			sut.getMasterOfAuction(mockAuctionID));
	}

	@Test
	void testRemoveMaster() {
		Auction auction = new Auction();

		when(auctionRepositoryFixture.findById(mockAuctionID))
			.thenReturn(Optional.of(auction));

		sut.removeMasterForCompletedAuction(mockAuctionID);

		verify(discoveryRepositoryFixture).deleteByAuction(auction);
	}
}
