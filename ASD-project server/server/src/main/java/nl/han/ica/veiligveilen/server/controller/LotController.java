package nl.han.ica.veiligveilen.server.controller;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.controller.interfaces.ILotController;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostLotRequest;
import nl.han.ica.veiligveilen.server.service.interfaces.ILotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Restful controller for the lots. This controller allows you to get all lots of a auction and add a new lot.
 */
@RestController
@RequestMapping(path = "/lot")
public class LotController implements ILotController {

	private final ILotService service;

	/**
	 * The constructor. Automatic dependency injection through spring.
	 * @param service The needed ILotService implementation.
	 */
	@Autowired
	public LotController(ILotService service) {
		this.service = service;
	}

	/**
	 * Gets all lots from an auction.
	 * @param auctionID A UUID. The ID of the selected auction.
	 * @return A Response with a List object filled with Lot objects.
	 */
	@Override
	@GetMapping(path = "/{auctionID}")
	public ResponseEntity<List<Lot>> getAllFromAuction(@PathVariable UUID auctionID) {
		return new ResponseEntity<>(this.service.getAllFromAuction(auctionID), HttpStatus.OK);
	}

	@Override
	@PutMapping(path = "")
	public ResponseEntity<Void> newLot(@RequestBody PostLotRequest request) {
		this.service.newLot(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
