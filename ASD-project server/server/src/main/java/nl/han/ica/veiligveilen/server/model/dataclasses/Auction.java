package nl.han.ica.veiligveilen.server.model.dataclasses;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

/**
 * Dataclass for Auction.
 */

@Entity(name = "auctions")
public class Auction {

	@Id
	@Type(type = "uuid-char")
	@Size(max = 36)
	@Column(name = "auction_id", nullable = false)
	private UUID auctionId;


	@Size(max = 256)
	@Column(name = "auction_name", nullable = false)
	private String auctionName;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "auction_type_id", nullable = false)
	private AuctionType auctionType;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	public Auction() { }

	/**
	 * The constructor.
	 * @param auctionId A UUID. Automatically generated when a new auction gets added to the database.
	 * @param auctionName A String. The name of the auction.
	 * @param category An instance of the Category class.
	 * @param auctionType An instance of the AuctionType class.
	 * @param startDate A Date. The start date of the auction.
	 * @param endDate A Date. The end date of the auction.
	 */
	public Auction (UUID auctionId, String auctionName, Category category, AuctionType auctionType,
					Date startDate, Date endDate) {
		this.auctionId = auctionId;
		this.auctionName = auctionName;
		this.category = category;
		this.auctionType = auctionType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public UUID getAuctionId() {
		return this.auctionId;
	}

	public String getAuctionName() {
		return this.auctionName;
	}

	public Category getCategory() {
		return this.category;
	}

	public AuctionType getAuctionType() {
		return this.auctionType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setAuctionID(UUID auctionId) {
		this.auctionId = auctionId;
	}
}
