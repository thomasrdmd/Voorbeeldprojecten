package nl.han.ica.veiligveilen.server.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.dao.IAuctionRepository;
import nl.han.ica.veiligveilen.server.dao.ILotRepository;
import nl.han.ica.veiligveilen.server.dao.ISellerRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostLotRequest;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LotServiceTests {

	private ILotRepository lotRepositoryFixture;
	private IAuctionRepository auctionRepositoryFixture;

	private LotService sut;

	private PostLotRequest postLotRequest;

	private UUID uuid;
	private Auction auction;

	@BeforeEach
	void setup(){
		lotRepositoryFixture = Mockito.mock(ILotRepository.class);
		ISellerRepository sellerRepositoryFixture = Mockito.mock(ISellerRepository.class);
		auctionRepositoryFixture = Mockito.mock(IAuctionRepository.class);

		sut = new LotService(lotRepositoryFixture, sellerRepositoryFixture, auctionRepositoryFixture);

		postLotRequest = new PostLotRequest();
		uuid = UUID.randomUUID();
		auction = new Auction();
	}

	@Test
	void testNewLotSuccess(){
		// Arrange

		// Act
		sut.newLot(postLotRequest);
		// Assert
		verify(lotRepositoryFixture).save(any());
	}

	@Test
	void testGetAllFromAuction() {
		List<Lot> lots = new ArrayList<>();
		lots.add(new Lot());


		when(auctionRepositoryFixture.findById(uuid)).thenReturn(Optional.ofNullable(auction));
		when(lotRepositoryFixture.findAllByAuction(auction)).thenReturn(lots);

		var result = sut.getAllFromAuction(uuid);

		Assertions.assertEquals(result, lots);
	}


}
