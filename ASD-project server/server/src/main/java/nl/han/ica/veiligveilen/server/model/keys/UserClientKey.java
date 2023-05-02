package nl.han.ica.veiligveilen.server.model.keys;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import nl.han.ica.veiligveilen.server.model.dataclasses.Client;
import nl.han.ica.veiligveilen.server.model.dataclasses.User;

@Embeddable
public class UserClientKey implements Serializable {
	@Serial
	private static final long serialVersionUID = -898339724049204977L;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public UserClientKey() {

	}

	public UserClientKey(User user, Client client) {
		this.client = client;
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserClientKey that = (UserClientKey) o;
		return Objects.equals(user, that.user) && Objects.equals(client, that.client);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, client);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
