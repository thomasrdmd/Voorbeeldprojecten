package nl.han.ica.veiligveilen.server.model.dataclasses;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;


@Entity(name = "seller")
public class Seller {

	@Id
	@Type(type = "uuid-char")
	@Size(max = 36)
	@Column(name = "seller_id", nullable = false)
	private UUID sellerID;

	@Size(max = 512)
	@Column(name = "first_name")
	private String firstName;

	@Size(max = 512)
	@Column(name = "last_name")
	private String lastName;

	@Size(max = 512)
	@Column(name = "email")
	private String email;

	@Size(max = 1028)
	@Column(name = "address")
	private String address;

	@Size(max = 255)
	@Column(name = "company")
	private String company;

	public Seller() {
	}

	public Seller(UUID sellerID, String firstName, String lastName, String email, String address, String company) {
		this.sellerID = sellerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.company = company;
	}

	public UUID getSellerID() {
		return this.sellerID;
	}

	public void setSellerID(UUID sellerID) {
		this.sellerID = sellerID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
