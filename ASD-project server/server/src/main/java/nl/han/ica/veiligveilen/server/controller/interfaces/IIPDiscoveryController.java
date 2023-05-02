package nl.han.ica.veiligveilen.server.controller.interfaces;

import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostIPDiscoveryRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.IPDiscoveryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to get information about masters
 */
@RequestMapping(path = "/ipregistry")
public interface IIPDiscoveryController {

	/**
	 * Get the IP adresses of all masters
	 * @return Response containing the status code and the list of ip adresses.
	 */
	@GetMapping(path = "")
	ResponseEntity<List<IPDiscoveryResponse>> getAll();

	/**
	 * Get the master of an auction
	 * @param auctionID ID of auction to get master of.
	 * @return Response containing the status code and the master's IP adress.
	 */
	@GetMapping(path = "/{auctionID}")
	ResponseEntity<IPDiscoveryResponse> getMasterOfAuction(@PathVariable UUID auctionID);

	/**
	 * Adds a new IP address to the database. Used when either a new auction starts or the master
	 * 		for an existing auction needs to be changed.
	 * @param request A PostIPDiscoveryRequest object with the IP address and auctionID that should be added.
	 * @return returns a response, the status code depends on if adding the address was successful.
	 */
	@PutMapping(path = "/new")
	ResponseEntity<List<IPDiscoveryResponse>> addNewMaster(@RequestBody PostIPDiscoveryRequest request);

	/**
	 * Update an IP address in the database.
	 * @param request request containing IP to update
	 * @return a list of ip addresses
	 */
	@PutMapping(path = "/update")
	ResponseEntity<List<IPDiscoveryResponse>> updateMaster(@RequestBody PostIPDiscoveryRequest request);

	/**
	 * Removes an IP address from the database. Only used when a auction is finished.
	 * @param auctionID A String of the auctionID
	 * @return returns a response, the status code depends on if removing the address was successful.
	 */
	@DeleteMapping(path = "/{auctionID}")
	ResponseEntity<Void> removeMaster(@PathVariable UUID auctionID);
}
