package nl.han.ica.veiligveilen.server.model.requestobjects;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


/**
 * The DTO for logging into the network.
 */
public class LoginDTO implements Serializable {
	@Serial
	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;
	private UUID clientId;

	public LoginDTO() {

	}

	/**
	 * The constructor for this DTO.
	 * @param username The username given by the Client.
	 * @param password The password given by the Client. This will be hashed by the Client.
	 * @param clientId The ID for the Client. Used to connect a user to a client. Giving in the request.
	 */
	public LoginDTO(String username, String password, UUID clientId) {
		this.username = username;
		this.password = password;
		this.clientId = clientId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}
}
