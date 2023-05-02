package nl.han.ica.veiligveilen.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.dao.IAuctionRepository;
import nl.han.ica.veiligveilen.server.dao.IAuctionTypeRepository;
import nl.han.ica.veiligveilen.server.dao.ICategoryRepository;
import nl.han.ica.veiligveilen.server.dao.ILotRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Auction;
import nl.han.ica.veiligveilen.server.model.exceptions.InternalServerErrorException;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostAuctionRequest;
import nl.han.ica.veiligveilen.server.model.responseobjects.AuctionResponse;
import nl.han.ica.veiligveilen.server.service.interfaces.IAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the auction endpoints.
 * Returns all auctions and saves a new auction in the database.
 */

@Service
public class AuctionService implements IAuctionService {

	private final IAuctionRepository auctionRepository;

	private final ICategoryRepository categoryRepository;
	private final IAuctionTypeRepository auctionTypeRepository;
	private final ILotRepository lotRepository;

	/**
	 * The constructor for this class. Automatically injected by Spring.
	 *
	 * @param auctionRepository     Repository needed to access the auctions in the database.
	 * @param categoryRepository    Repository needed to access the category of the auctions.
	 * @param auctionTypeRepository Repository needed to access the types of the auctions.
	 * @param lotRepository			Repository needed to acces the lots.
	 */
	@Autowired
	public AuctionService(IAuctionRepository auctionRepository, ICategoryRepository categoryRepository,
			IAuctionTypeRepository auctionTypeRepository, ILotRepository lotRepository) {
		this.auctionRepository = auctionRepository;
		this.categoryRepository = categoryRepository;
		this.auctionTypeRepository = auctionTypeRepository;
		this.lotRepository = lotRepository;
	}

	/**
	 * Gets all auctions from the database.
	 * @return List object filled with Auction objects. (A Auction object includes only one autction).
	 */
	@Override
	public List<AuctionResponse> getAllAuctions() {
		List<AuctionResponse> list = new ArrayList<>();

		auctionRepository.findAll().forEach(auction -> list.add(new AuctionResponse(auction.getAuctionId(),
			auction.getAuctionName(), auction.getCategory(),
			auction.getAuctionType().getAuctionTypeName(),
			auction.getStartDate(), auction.getEndDate(),
			lotRepository.findAllByAuction(auction))));

		return list;
	}

	/**
	 * Creates a new auction in the database.
	 * @param request a request object with nearly all the needed data for a new auction. The auctionID gets
	 *                generated in this function.
	 */
	public void newAuction(PostAuctionRequest request) {
		Auction auction = new Auction(UUID.randomUUID(), request.getAuctionName(),
			this.categoryRepository.findById(request.getCategoryId()).orElse(null),
			this.auctionTypeRepository.findById(request.getAuctionTypeId()).orElse(null),
			request.getStartDate(), request.getEndDate());

		if (auction.getAuctionType() == null || auction.getCategory() == null) {
			throw new InternalServerErrorException(
				"Something went wrong with the auction's type or category.");
		}

		this.auctionRepository.save(auction);
	}
}
