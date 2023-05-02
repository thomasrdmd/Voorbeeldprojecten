package nl.han.ica.veiligveilen.server.controller;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostLotRequest;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.service.interfaces.ILotService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

public class LotControllerTests {

	private ILotService lotServiceFixture;

	private LotController sut;

	private PostLotRequest postLotRequest;

	@BeforeEach
	void setup(){
		lotServiceFixture = Mockito.mock(ILotService.class);

		sut = new LotController(lotServiceFixture);

		postLotRequest = new PostLotRequest();
	}

	@Test
	void testNewLotSuccess(){
		// Act
		var result = sut.newLot(postLotRequest);
		// Assert
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testGetAllLotsSuccess() {
		List<Lot> lots = new ArrayList<>();
		lots.add(new Lot());

		UUID uuid = UUID.randomUUID();

		when(lotServiceFixture.getAllFromAuction(uuid)).thenReturn(lots);

		var result = sut.getAllFromAuction(uuid);

		Assertions.assertEquals(result.getBody(), lots);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
