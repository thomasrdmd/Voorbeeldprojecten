package nl.han.ica.veiligveilen.server.model.dataclasses;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

/**
 * The dataclass for the auction type. The auction type decides what type of bidding will be done.
 * The server won't add new types.
 */
@Entity(name = "auction_type")
public class AuctionType {

	@Id
	@Type(type = "uuid-char")
	@Size(max = 36)
	@Column(name = "auction_type_id")
	private UUID auctionTypeId;

	@Size(max = 128)
	@Column(name = "auction_type_name")
	private String auctionTypeName;

	/**
	 * The constructor.
	 * @param auctionTypeId A UUID. Will be automatically generated when a type is added to the database.
	 * @param auctionTypeName A String. The name of the type, describing/deciding what kind of bidding will be done.
	 */
	public AuctionType(UUID auctionTypeId, String auctionTypeName) {
		this.auctionTypeId = auctionTypeId;
		this.auctionTypeName = auctionTypeName;
	}

	public AuctionType() {

	}

	public UUID getAuctionTypeId() {
		return this.auctionTypeId;
	}

	public void setAuctionTypeId(UUID auctionTypeId) {
		this.auctionTypeId = auctionTypeId;
	}

	public String getAuctionTypeName() {
		return this.auctionTypeName;
	}

	public void setAuctionTypeName(String auctionTypeName) {
		this.auctionTypeName = auctionTypeName;
	}
}
