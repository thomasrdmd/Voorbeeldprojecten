package nl.han.ica.veiligveilen.server.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.AuctionType;
import nl.han.ica.veiligveilen.server.model.dataclasses.Category;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostAuctionRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.AuctionResponse;
import nl.han.ica.veiligveilen.server.service.AuctionService;
import nl.han.ica.veiligveilen.server.service.interfaces.IAuctionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;


public class AuctionControllerTests {

	private IAuctionService auctionServiceFixture;

	private AuctionController sut;

	private PostAuctionRequest postAuctionRequest;


	@BeforeEach
	void setup(){
		auctionServiceFixture = Mockito.mock(AuctionService.class);

		sut = new AuctionController(auctionServiceFixture);

		postAuctionRequest = new PostAuctionRequest();
	}

	@Test
	void testNewAuctionSuccess(){
		// Act
		var result = sut.newAuction(postAuctionRequest);
		// Assert
		verify(auctionServiceFixture).newAuction(postAuctionRequest);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testGetAllActionsSuccess() {
		// Arrange
		List<Lot> lotList = new ArrayList<>();

		List<AuctionResponse> auctionList = new ArrayList<>();
		auctionList.add(new AuctionResponse(UUID.randomUUID(),
			"auctionName", new Category(),
			"AuctionType", Date.from(Instant.now()), Date.from(Instant.now()),
		lotList));

		when(auctionServiceFixture.getAllAuctions()).thenReturn(auctionList);

		// Act
		var result = sut.getAllAuctions();

		// Assert
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(result.getBody(), auctionList);

	}
}
