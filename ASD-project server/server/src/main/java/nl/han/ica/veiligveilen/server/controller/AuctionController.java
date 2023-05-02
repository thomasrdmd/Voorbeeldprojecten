package nl.han.ica.veiligveilen.server.controller;

import java.util.List;
import nl.han.ica.veiligveilen.server.controller.interfaces.IAuctionController;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostAuctionRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.AuctionResponse;
import nl.han.ica.veiligveilen.server.service.interfaces.IAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Restful controller for auctions.
 */
@RestController
@RequestMapping(path = "/auctions")
public class AuctionController implements IAuctionController {

	private final IAuctionService auctionService;

	/**
	 * The constructor. Automatic dependency injection through Spring.
	 * @param auctionService The needed implementation of the IAuctionService.
	 */
	@Autowired
	public AuctionController(IAuctionService auctionService) {
		this.auctionService = auctionService;
	}

	/**
	 * Gets all auctions in the database.
	 * @return A Response containing a List object filled with Auction objects.
	 */
	@GetMapping(path = "")
	@Override
	public ResponseEntity<List<AuctionResponse>> getAllAuctions() {
		return new ResponseEntity<>(this.auctionService.getAllAuctions(), HttpStatus.OK);
	}

	/**
	 * Adds a new auction to the database.
	 * @param request A request with all the data needed for a new Auction.
	 * @return Returns a response, the status depends on if the new auction was successfully added.
	 */
	@Override
	@PutMapping("")
	public ResponseEntity<Void> newAuction(@RequestBody PostAuctionRequest request) {
		this.auctionService.newAuction(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
