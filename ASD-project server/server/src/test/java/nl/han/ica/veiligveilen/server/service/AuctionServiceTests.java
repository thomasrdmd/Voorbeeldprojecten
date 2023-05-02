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
import nl.han.ica.veiligveilen.server.dao.IAuctionTypeRepository;
import nl.han.ica.veiligveilen.server.dao.ICategoryRepository;
import nl.han.ica.veiligveilen.server.dao.ILotRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.AuctionType;
import nl.han.ica.veiligveilen.server.model.dataclasses.Category;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostAuctionRequest;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuctionServiceTests {

	private IAuctionRepository auctionRepositoryFixture;

	private ICategoryRepository categoryRepositoryFixture;

	private IAuctionTypeRepository auctionTypeRepositoryFixture;

	private ILotRepository lotRepositoryFixture;

	private AuctionService sut;

	private PostAuctionRequest request;
	private Auction auction;

	@BeforeEach
	void setup(){
		auctionRepositoryFixture = Mockito.mock(IAuctionRepository.class);
		categoryRepositoryFixture = Mockito.mock(ICategoryRepository.class);
		auctionTypeRepositoryFixture = Mockito.mock(IAuctionTypeRepository.class);
		lotRepositoryFixture = Mockito.mock(ILotRepository.class);

		sut = new AuctionService(auctionRepositoryFixture, categoryRepositoryFixture, auctionTypeRepositoryFixture,
			lotRepositoryFixture);

		request = new PostAuctionRequest();
		auction = new Auction(UUID.randomUUID(),
			"name",
			new Category(UUID.randomUUID(), "category"),
			new AuctionType(UUID.randomUUID(), "type"),
			Date.from(Instant.now()), Date.from(Instant.now()));
	}

	@Test
	void testGetAllAuctions () {
		List<Auction> auctionList = new ArrayList<>();
		auctionList.add(auction);

		when(auctionRepositoryFixture.findAll()).thenReturn(auctionList);

		var result = sut.getAllAuctions();

		Assertions.assertEquals(result.get(0).auctionId(), auction.getAuctionId());
		Assertions.assertEquals(result.get(0).auctionName(), auction.getAuctionName());
	}

	@Test
	void testAddAuction() {
		Mockito.when(categoryRepositoryFixture.findById(request.getCategoryId())).thenReturn(
			Optional.of(new Category()));

		Mockito.when(auctionTypeRepositoryFixture.findById(request.getAuctionTypeId())).thenReturn(
			Optional.of(new AuctionType()));

		sut.newAuction(request);

		verify(auctionRepositoryFixture).save(Mockito.any());
	}
}
