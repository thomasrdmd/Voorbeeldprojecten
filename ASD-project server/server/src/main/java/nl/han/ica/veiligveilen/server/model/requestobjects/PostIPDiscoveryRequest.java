package nl.han.ica.veiligveilen.server.model.requestobjects;

import java.util.UUID;

public class PostIPDiscoveryRequest {

	private String ip;

	private UUID auctionID;

	public PostIPDiscoveryRequest() {
	}

	public PostIPDiscoveryRequest(String ip, UUID auctionID) {
		this.ip = ip;
		this.auctionID = auctionID;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public UUID getAuctionID() {
		return auctionID;
	}

	public void setAuctionID(UUID auctionID) {
		this.auctionID = auctionID;
	}
}
