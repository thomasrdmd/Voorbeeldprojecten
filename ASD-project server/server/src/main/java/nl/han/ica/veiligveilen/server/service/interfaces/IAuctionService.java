package nl.han.ica.veiligveilen.server.service.interfaces;

import java.util.List;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostAuctionRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.AuctionResponse;

/**
 * 
 */
public interface IAuctionService {
	List<AuctionResponse> getAllAuctions();

	void newAuction(PostAuctionRequest request);
}
