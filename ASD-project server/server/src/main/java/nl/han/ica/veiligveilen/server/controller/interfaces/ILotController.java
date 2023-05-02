package nl.han.ica.veiligveilen.server.controller.interfaces;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostLotRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for creating and getting lots
 */
@RequestMapping(path = "/lot")
public interface ILotController {

	/**
	 * Get all lots from specific auction
	 * @param auctionID ID of the auction to ge the lots from
	 * @return A list of lots from the auction
	 */
	@GetMapping(path = "/{auctionID}")
	ResponseEntity<List<Lot>> getAllFromAuction(@PathVariable UUID auctionID);

	/**
	 * Create a new lot for an auction
	 * @param request containing lot information
	 * @return response containing status code
	 */
	@PutMapping(path = "")
	ResponseEntity<Void> newLot(@RequestBody PostLotRequest request);
}
