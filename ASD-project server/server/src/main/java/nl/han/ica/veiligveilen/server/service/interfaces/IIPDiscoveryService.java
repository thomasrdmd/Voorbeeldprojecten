package nl.han.ica.veiligveilen.server.service.interfaces;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.IPDiscovery;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostIPDiscoveryRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.IPDiscoveryResponse;

/**
 * Service layer for the IPDiscovery.
 * Allows for retrieving IP addresses in a multitute of ways, and adding, updating and removing IP addresses.
 */
public interface IIPDiscoveryService {
	/**
	 * Retrieves a List of IPDiscovery data objects.
	 * @return
	 */
	List<IPDiscovery> getAll();

	/**
	 * Retrieves a List of IPDiscoveryResponse objects.
	 * @return
	 */
	List<IPDiscoveryResponse> getAllAsResponse();

	/**
	 * Retrieves the IP address of the master of the given auction.
	 * @param auctionID The UUID of the auction.
	 * @return The IP address of the master.
	 */
	IPDiscoveryResponse getMasterOfAuction(UUID auctionID);

	/**
	 * Retrieves the two latest added ip addresses. Used when a new master wants to join the network
	 * or replaces a old master. If there are less than two ip addresses in the db, the list shrinks accordingly.
	 * @return A list of two or less IPDiscoveryresponse objects.
	 */
	List<IPDiscoveryResponse> getLatest();

	/**
	 * Saves a new master.
	 * @param request The PostIPDIscoveryRequest object with the relevant information.
	 */
	void addNewMaster(PostIPDiscoveryRequest request);

	/**
	 * Updates the master for an auction.
	 * @param request The PostIPDIscoveryRequest object with the relevant information.
	 */
	void updateMaster(PostIPDiscoveryRequest request);

	/**
	 * Removes the master for a completed auction
	 * @param auctionID The UUID of the completed auction.
	 */
	void removeMasterForCompletedAuction(UUID auctionID);
}
