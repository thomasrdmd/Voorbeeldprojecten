package nl.han.ica.veiligveilen.server.service;

import java.util.List;
import nl.han.ica.veiligveilen.server.dao.IStrategyRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;
import nl.han.ica.veiligveilen.server.model.exceptions.InternalServerErrorException;
import nl.han.ica.veiligveilen.server.service.interfaces.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service class for Strategy.
 * This class can add new strategies to the database, and request all strategies in the database.
 */

@Service
public class StrategyService implements  IStrategyService {

	private final IStrategyRepository strategyRepository;

	/**
	 * Constructor for StragegyService.
	 * @param strategyRepository The needed implementation of the IStrategyRepository.
	 */
	@Autowired
	public StrategyService(IStrategyRepository strategyRepository) {
		this.strategyRepository = strategyRepository;
	}

	/**
	 * This function saves a list of strategies. The list can contain a single strategy.
	 *
	 * @param strategiesList A List of Strategies that need to be saved in the database.
	 */
	@Override
	public void saveStrategies(List<Strategy> strategiesList) {
		this.strategyRepository.saveAll(strategiesList);
	}


	/**
	 * This function gets all strategies in the database.
	 * @return returns a list of Strategies objects.
	 */
	@Override
	public List<Strategy> getAllStrategies() {
		return this.strategyRepository.findAll();
	}
}
