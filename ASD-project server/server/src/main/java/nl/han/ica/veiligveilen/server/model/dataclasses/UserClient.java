package nl.han.ica.veiligveilen.server.model.dataclasses;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import nl.han.ica.veiligveilen.server.model.keys.UserClientKey;


/**
 * Data class for the UserClient table in the database. This table connects users to the clients they use.
 * Needed for Non-repudiation.
 */
@Entity(name = "user_clients")
public class UserClient {

	@EmbeddedId
	private UserClientKey key;

	public UserClient() {

	}

	public UserClient(UserClientKey key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserClient that = (UserClient) o;
		return Objects.equals(key, that.key);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	public UserClientKey getKey() {
		return this.key;
	}

	public void setKey(UserClientKey key) {
		this.key = key;
	}
}
