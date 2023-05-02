package nl.han.ica.veiligveilen.server.model.dataclasses;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
/**
 * Dataclass for the IP addresses of the masters of a auction.
 */

@Entity(name = "discovery_ips")
public class IPDiscovery {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int discoveryId;


	@Size(max = 512)
	@Column(name = "ip")
	private String ip;

	@OneToOne
	@JoinColumn(name = "auction_id")
	private Auction auction;

	/**
	 * The constructor.
	 * @param id An int.
	 * @param ip A String. The ip address of a Master Node in the network.
	 * @param auction A String. The id of the auction the client at the ip address is the master of.
	 */
	public IPDiscovery(int id, String ip, Auction auction) {
		this.discoveryId = id;
		this.ip = ip;
		this.auction = auction;
	}

	public IPDiscovery() {

	}

	public int getDiscoveryId() {
		return this.discoveryId;
	}

	public void setDiscoveryId(int discoveryId) {
		this.discoveryId = discoveryId;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Auction getAuction() {
		return this.auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}
}
