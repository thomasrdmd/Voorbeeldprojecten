package nl.han.ica.veiligveilen.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.dao.IAuctionRepository;
import nl.han.ica.veiligveilen.server.dao.ILotRepository;
import nl.han.ica.veiligveilen.server.dao.ISellerRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostLotRequest;
import nl.han.ica.veiligveilen.server.service.interfaces.ILotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Lot.
 * This class can get all lots for an auction, and save a new lot.
 */

@Service
public class LotService implements ILotService {

	private final ILotRepository lotRepository;

	private final ISellerRepository sellerRepository;
	private final IAuctionRepository auctionRepository;

	/**
	 * Constructor for this class
	 *
	 * @param lotRepository     The needed repository for this class. Automatically injected by Spring.
	 * @param sellerRepository  The needed repository to select a existing seller from the database.
	 * @param auctionRepository	Needed repository to select an existing auction from the database.
	 */
	@Autowired
	public LotService(ILotRepository lotRepository, ISellerRepository sellerRepository,
					IAuctionRepository auctionRepository) {
		this.lotRepository = lotRepository;
		this.sellerRepository = sellerRepository;
		this.auctionRepository = auctionRepository;
	}

	/**
	 * This function gets all Lots from an auction.
	 * @param auctionId the ID for the auction whose Lots have been requested
	 * @return returns a List of Lot objects.
	 */
	@Override
	public List<Lot> getAllFromAuction(UUID auctionId) {
		Optional<Auction> auction = this.auctionRepository.findById(auctionId);

		if (auction.isPresent()) {
			return this.lotRepository.findAllByAuction(auction.get());
		}
		return new ArrayList<>();
	}

	/**
	 * This function adds a new Lot in the database. All needed data should already be in the given Lot object.
	 * @param request A request object with nearly all the needed data for a new lot. The lotID gets generated in
	 *                this function.
	 */
	@Override
	public void newLot(PostLotRequest request) {
		Lot lot = new Lot(UUID.randomUUID(),
			request.getLotNr(), auctionRepository.findById(request.getAuctionId()).orElse(null),
			request.getLotName(), request.getReservePrice(), request.getDescription(),
			sellerRepository.findById(request.getSellerId()).orElse(null));

		this.lotRepository.save(lot);
	}
}
