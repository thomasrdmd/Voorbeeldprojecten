package nl.han.ica.veiligveilen.server.model.dataclasses;


import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

/**
 * Dataclass for the Users table in the database.
*/

@Entity(name = "users")
public class User implements Serializable {
	@Serial
	private static final long serialVersionUID = -2476813359552690147L;

	@Id
	@Type(type = "uuid-char")
	@Size(max = 36)
	@Column(name = "user_id", nullable = false)
	private UUID userId;

	@Size(max = 256)
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Size(max = 512)
	@Column(name = "password", nullable = false)
	private String password;

	public User() {

	}

	/**
	 *
	 * @param userId The id for a user. This is a UUID.
	 * @param username The given username.
	 * @param password The given password of an account. This is hashed.
	 */
	public User(UUID userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public UUID getUserId() {
		return this.userId;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
