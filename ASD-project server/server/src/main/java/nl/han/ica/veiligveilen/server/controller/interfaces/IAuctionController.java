package nl.han.ica.veiligveilen.server.controller.interfaces;

import java.util.List;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostAuctionRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.AuctionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to get auctions
 */
@RequestMapping(path = "/auctions")
public interface IAuctionController {

	/**
	 * Get all auctions.
	 * @return Response entity containing status code of the request and all Auctions.
	 */
	@GetMapping(path = "")
	ResponseEntity<List<AuctionResponse>> getAllAuctions();

	/**
	 * Create new auction.
	 * @return containing status code of the request.
	 */
	@PutMapping(path = "")
	ResponseEntity<Void> newAuction(@RequestBody PostAuctionRequest request);

}
