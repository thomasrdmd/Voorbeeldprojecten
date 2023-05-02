package nl.han.ica.veiligveilen.server.model.requestobjects;

import java.util.Date;
import java.util.UUID;

public class PostAuctionRequest {

	private String auctionName;

	private UUID categoryId;

	private UUID auctionTypeId;

	private Date startDate;

	private Date endDate;

	public PostAuctionRequest() {
	}

	public PostAuctionRequest(String auctionName, UUID categoryId, UUID auctionTypeId, Date startDate,
								Date endDate) {
		this.auctionName = auctionName;
		this.categoryId = categoryId;
		this.auctionTypeId = auctionTypeId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getAuctionName() {
		return auctionName;
	}

	public void setAuctionName(String auctionName) {
		this.auctionName = auctionName;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public UUID getAuctionTypeId() {
		return auctionTypeId;
	}

	public void setAuctionTypeId(UUID auctionTypeId) {
		this.auctionTypeId = auctionTypeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
