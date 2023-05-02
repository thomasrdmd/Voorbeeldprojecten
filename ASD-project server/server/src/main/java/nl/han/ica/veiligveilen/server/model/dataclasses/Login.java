package nl.han.ica.veiligveilen.server.model.dataclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * The dataclass for the recorded logins. Needed for Non-repudiation.
 */
@Entity(name = "logins")
public class Login {

	@Id
	@Type(type = "uuid-char")
	@Size(max = 36)
	@Column(name = "login_id")
	private UUID loginId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JoinColumn(name = "client_id")
	private UserClient userClient;

	@Column(name = "timestamp")
	private Date timestamp;

	@Column(name = "ip_address")
	private String ipAddress;

	public Login(UUID loginId, UserClient userClient, Date timestamp, String ipAddress) {
		this.loginId = loginId;
		this.userClient = userClient;
		this.timestamp = timestamp;
		this.ipAddress = ipAddress;
	}


	public Login() {
	}

	public UUID getLoginId() {
		return this.loginId;
	}

	public void setLoginId(UUID loginId) {
		this.loginId = loginId;
	}

	public UserClient getUserClient() {
		return this.userClient;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
