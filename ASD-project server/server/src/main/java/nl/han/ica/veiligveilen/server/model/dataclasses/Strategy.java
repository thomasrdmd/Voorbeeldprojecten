package nl.han.ica.veiligveilen.server.model.dataclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import nl.han.ica.veiligveilen.server.model.keys.StrategyKey;

/**
 * Dataclass for the Strategies table in the database.
 */
@Entity(name = "strategies")
public class Strategy {
	@EmbeddedId
	private StrategyKey key;


	@Column(name = "strategy_text", nullable = false)
	private String strategyText;

	@Column(name = "timestamp", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;

	@Column(name = "has_won", nullable = false)
	private boolean hasWon;

	public Strategy() {
	}

	/**
	 * The constructor
	 * @param key a StrategyKey object.
	 * @param strategyText a String. The strategy in text form.
	 * @param timestamp a String. The timestamp of when the strategy was sent to the database.
	 *                  Automatically generated in the server.
	 * @param hasWon a boolean.Shows if the strategy has won.
	 */
	public Strategy(StrategyKey key, String strategyText, Date timestamp, boolean hasWon) {
		this.key = key;
		this.strategyText = strategyText;
		this.timestamp = timestamp;
		this.hasWon = hasWon;
	}

	public StrategyKey getKey() {
		return this.key;
	}

	public String getStrategyText() {
		return this.strategyText;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public Date getTimestamp() {
		return this.timestamp;
	}

	public boolean hasWon() {
		return this.hasWon;
	}
}
