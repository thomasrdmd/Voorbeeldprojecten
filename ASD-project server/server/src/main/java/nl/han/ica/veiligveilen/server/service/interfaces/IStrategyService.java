package nl.han.ica.veiligveilen.server.service.interfaces;

import java.util.List;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;

/**
 *Service layer for the strategies.
 * Allows saving a list of strategies and returnin a list of strategies.
 */
public interface IStrategyService {

	/**
	 * Saves a list of strategies.
	 * @param strategiesList A List object with Strategy objects.
	 */
	void saveStrategies(List<Strategy> strategiesList);

	/**
	 * Returns all strategies.
	 * @return A List object with Strategy objects.
	 */
	List<Strategy> getAllStrategies();
}
