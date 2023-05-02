package nl.han.ica.veiligveilen.server.model.keys;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import nl.han.ica.veiligveilen.server.model.dataclasses.Login;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;

/**
 * Key voor de Strategy dataclass.
 */
@Embeddable
public class StrategyKey implements Serializable {

	@OneToOne
	@JoinColumn(name = "lot_id")
	private Lot lot;

	@OneToOne
	@JoinColumn(name = "login_id")
	private Login login;

	public StrategyKey(Lot lot, Login login) {
		this.lot = lot;
		this.login = login;
	}

	public StrategyKey() {

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		StrategyKey that = (StrategyKey) o;
		return this.lot.equals(that.lot) && this.login.equals(that.login);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.lot, this.login);
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}
