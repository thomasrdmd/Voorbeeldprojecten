package nl.han.ica.veiligveilen.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.dao.IAuctionRepository;
import nl.han.ica.veiligveilen.server.dao.IIPDiscoveryRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.IPDiscovery;
import nl.han.ica.veiligveilen.server.model.exceptions.AlreadyExistsException;
import nl.han.ica.veiligveilen.server.model.exceptions.InternalServerErrorException;
import nl.han.ica.veiligveilen.server.model.exceptions.NoMasterFoundException;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostIPDiscoveryRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.IPDiscoveryResponse;
import nl.han.ica.veiligveilen.server.service.interfaces.IIPDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for IPDiscovery.
 * Just returns all known IP adresses from the database.
 */

@Service
public class IPDiscoveryService implements IIPDiscoveryService {

	private final IIPDiscoveryRepository ipDiscoveryRepository;

	private final IAuctionRepository auctionRepository;

	/**
	 * Constructor for this class. Automaticly injected by spring.
	 *
	 * @param ipDiscoveryRepository The needed repository for the IP discovery.
	 * @param auctionRepository Needed to get the related auction from the auctionID.
	 */
	@Autowired
	public IPDiscoveryService(IIPDiscoveryRepository ipDiscoveryRepository, IAuctionRepository auctionRepository) {
		this.ipDiscoveryRepository = ipDiscoveryRepository;
		this.auctionRepository = auctionRepository;
	}

	/**
	 * Returns all IP adresses from the database. These IP addresses should be from clients currently in the
	 * P2P network.
	 * @return List object filled with IPDiscovery objects.
	 */
	@Override
	public List<IPDiscovery> getAll() {
		return ipDiscoveryRepository.findAll();
	}

	@Override
	public List<IPDiscoveryResponse> getAllAsResponse() {
		List<IPDiscoveryResponse> responseList = new ArrayList<>();

		getAll().forEach(ipDiscovery -> responseList.add(new IPDiscoveryResponse(ipDiscovery.getDiscoveryId(),
			ipDiscovery.getIp(), ipDiscovery.getAuction().getAuctionId())));

		return responseList;
	}


	/**
	 * Gets the IP address of the master of an auction.
	 * @param auctionID The ID of the auction.
	 * @return An IPDiscovery object with the IP address and the auctionID for good measure.
	 */
	@Override
	public IPDiscoveryResponse getMasterOfAuction(UUID auctionID) {
		Optional<IPDiscovery> ipDiscovery = this.ipDiscoveryRepository
			.findByAuction(auctionRepository.findById(auctionID).orElse(null));

		if (ipDiscovery.isEmpty()) {
			throw new NoMasterFoundException("No master found for the auction: " + auctionID);
		}

		IPDiscovery nonOptionalDisco = ipDiscovery.get();

		return new IPDiscoveryResponse(nonOptionalDisco.getDiscoveryId(),
			nonOptionalDisco.getIp(), nonOptionalDisco.getAuction().getAuctionId());

	}

	@Override
	public List<IPDiscoveryResponse> getLatest() {
		List<IPDiscoveryResponse> latestList = new ArrayList<>();
		List<IPDiscoveryResponse> fullList = getAllAsResponse();

		if (fullList != null && !fullList.isEmpty() && fullList.size() > 1) {
			latestList.add(fullList.get(fullList.size() - 2));
			if (fullList.size() >= 3) {
				latestList.add(fullList.get(fullList.size() - 3));
			}

		}

		return latestList;
	}

	/**
	 * Inserts the ip adress of a new master into the database, together with the auctionID.
	 * @param request request dataclass with the IP address of the new master and the AuctionID which got
	 *                said new master.
	 */
	@Override
	public void addNewMaster(PostIPDiscoveryRequest request) {
		IPDiscovery ipDiscovery = new IPDiscovery(0, request.getIp(),
			auctionRepository.findById(request.getAuctionID()).orElse(null));

		if (ipDiscovery.getAuction() == null) {
			throw new InternalServerErrorException(
				"Something went wrong retrieving the related auction for the new master");
		}

		List<IPDiscovery> list = getAll();

		list.stream().filter(ipDiscovery1 -> ipDiscovery1.getAuction().equals(ipDiscovery.getAuction()))
			.forEach(ipDiscovery1 -> {
				throw new AlreadyExistsException(
					"There already is a master for the auction " + request.getAuctionID());
			});

		this.ipDiscoveryRepository.save(ipDiscovery);
	}


	/**
	 * Updates the master of an auction.
	 * @param request request dataclass with the IP address of the updated master and the AuctionID which got
	 *                said new master.
	 */
	@Override
	public void updateMaster(PostIPDiscoveryRequest request) {
		IPDiscovery ipDiscovery = new IPDiscovery(0, request.getIp(),
			auctionRepository.findById(request.getAuctionID()).orElse(null));

		if (ipDiscovery.getAuction() == null) {
			throw new InternalServerErrorException(
				"Something went wrong retrieving the related auction for the updated master");
		}

		List<IPDiscovery> list = getAll();

		list.stream().filter(ipDiscovery1 -> ipDiscovery1.getAuction().equals(ipDiscovery.getAuction()))
			.forEach(ipDiscovery1 -> ipDiscovery.setDiscoveryId(ipDiscovery1.getDiscoveryId()));

		this.ipDiscoveryRepository.save(ipDiscovery);
	}

	/**
	 * Removes a completed auction from the list of masters in the db.
	 * @param auctionID String, the ID of the auction.
	 */
	@Override
	@Transactional
	public void removeMasterForCompletedAuction(UUID auctionID) {
		this.ipDiscoveryRepository.deleteByAuction(auctionRepository
			.findById(auctionID).orElse(null));
	}
}
