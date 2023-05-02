package nl.han.ica.veiligveilen.server.controller;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.controller.interfaces.IIPDiscoveryController;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostIPDiscoveryRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.IPDiscoveryResponse;
import nl.han.ica.veiligveilen.server.service.interfaces.IIPDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The REST controller for IPDiscovery. The IP registry is used to connect to the network through the IP addresses of
 * the Master Nodes. The IP adresses of the Masters can be added and removed from the database through this
 * controller.
 */
@RestController
@RequestMapping(path = "/ipregistry")
public class IPDiscoveryController implements IIPDiscoveryController {

	private final IIPDiscoveryService ipDiscoveryService;

	@Autowired
	public IPDiscoveryController(IIPDiscoveryService ipDiscoveryService) {
		this.ipDiscoveryService = ipDiscoveryService;
	}

	@Override
	@GetMapping(path = "")
	public ResponseEntity<List<IPDiscoveryResponse>> getAll() {
		return new ResponseEntity<>(this.ipDiscoveryService.getAllAsResponse(), HttpStatus.OK);
	}

	@Override
	@GetMapping(path = "/{auctionID}")
	public ResponseEntity<IPDiscoveryResponse> getMasterOfAuction(@PathVariable UUID auctionID) {
		return new ResponseEntity<>(this.ipDiscoveryService.getMasterOfAuction(auctionID), HttpStatus.OK);
	}


	@Override
	@PutMapping(path = "/new")
	public ResponseEntity<List<IPDiscoveryResponse>> addNewMaster(@RequestBody PostIPDiscoveryRequest request) {
		this.ipDiscoveryService.addNewMaster(request);

		return new ResponseEntity<>(this.ipDiscoveryService.getLatest(), HttpStatus.OK);
	}

	@Override
	@PutMapping(path = "/update")
	public ResponseEntity<List<IPDiscoveryResponse>> updateMaster(@RequestBody PostIPDiscoveryRequest request) {
		this.ipDiscoveryService.updateMaster(request);
		return new ResponseEntity<>(this.ipDiscoveryService.getLatest(), HttpStatus.OK);
	}


	@Override
	@DeleteMapping(path = "/{auctionID}")
	public ResponseEntity<Void> removeMaster(@PathVariable UUID auctionID) {
		this.ipDiscoveryService.removeMasterForCompletedAuction(auctionID);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
