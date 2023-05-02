package nl.han.ica.veiligveilen.server.controller;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostIPDiscoveryRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.IPDiscoveryResponse;
import nl.han.ica.veiligveilen.server.service.interfaces.IIPDiscoveryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

public class IPDiscoveryControllerTests {

	private IIPDiscoveryService discoveryServiceFixture;

	private IPDiscoveryController sut;

	private PostIPDiscoveryRequest request;

	private final UUID auctionID = UUID.randomUUID();

	@BeforeEach
	void setup(){
		discoveryServiceFixture = Mockito.mock(IIPDiscoveryService.class);

		sut = new IPDiscoveryController(discoveryServiceFixture);

		request = new PostIPDiscoveryRequest();
	}

	@Test
	void testGetMasterOfAuction() {
		IPDiscoveryResponse ipDiscoveryResponse =
			new IPDiscoveryResponse(1, "123.45.67.89", auctionID);

		UUID uuid = UUID.randomUUID();
		when(discoveryServiceFixture.getMasterOfAuction(uuid)).thenReturn(ipDiscoveryResponse);

		var result = sut.getMasterOfAuction(uuid);

		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(result.getBody(), ipDiscoveryResponse);
	}

	@Test
	void testGetAllAuctions() {
		List<IPDiscoveryResponse> ipDiscoveryList = new ArrayList<>();
		ipDiscoveryList.add(new IPDiscoveryResponse(1, "123.45.67.89", auctionID));

		when(discoveryServiceFixture.getAllAsResponse()).thenReturn(ipDiscoveryList);

		var result = sut.getAll();

		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(result.getBody(), ipDiscoveryList);
	}

	@Test
	void testAddNewMasterSuccess(){
		// Act
		var result = sut.addNewMaster(request);
		// Assert
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}


	@Test
	void testRemoveMasterSuccess(){
		// Act
		var result = sut.removeMaster(auctionID);
		// Assert
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

	}

}
