package nl.han.ica.veiligveilen.server.service.interfaces;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostLotRequest;

/**
 * Service layer for lot.
 * Allows adding a lot and returns a list of the lost from the given auction.
 */
public interface ILotService {

	/**
	 * Returns a list of Lot objects that are bound to the auctionId.
	 * @param auctionId The UUID of an auciton/.
	 * @return List object filled with Lot objects.
	 */
	List<Lot> getAllFromAuction(UUID auctionId);

	/**
	 * Save a new lot.
	 * @param request A PostLotRequest object.
	 */
	void newLot(PostLotRequest request);
}
