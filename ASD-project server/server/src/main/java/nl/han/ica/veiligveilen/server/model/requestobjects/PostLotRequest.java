package nl.han.ica.veiligveilen.server.model.requestobjects;


import java.math.BigDecimal;
import java.util.UUID;

public class PostLotRequest {

	private int lotNr;

	private UUID auctionId;

	private String lotName;

	private BigDecimal reservePrice;

	private String description;

	private UUID sellerId;

	public PostLotRequest() {
	}

	public PostLotRequest(int lotNr, UUID auctionId, String lotName, BigDecimal reservePrice,
							String description, UUID sellerId) {
		this.lotNr = lotNr;
		this.auctionId = auctionId;
		this.lotName = lotName;
		this.reservePrice = reservePrice;
		this.description = description;
		this.sellerId = sellerId;
	}

	public int getLotNr() {
		return lotNr;
	}

	public void setLotNr(int lotNr) {
		this.lotNr = lotNr;
	}

	public UUID getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(UUID auctionId) {
		this.auctionId = auctionId;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public BigDecimal getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(BigDecimal reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
	}
}
