package nl.han.ica.veiligveilen.server.controller;

import java.util.List;
import nl.han.ica.veiligveilen.server.controller.interfaces.IStrategyController;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostStrategyListRequest;
import nl.han.ica.veiligveilen.server.service.interfaces.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The restful controller for strategy.
 * You can save a single or multiple new strategies or get all strategies in the database.
 */
@RestController
@RequestMapping(path = "/strategy")
public class StrategyController implements IStrategyController {

	private final IStrategyService strategyService;

	/**
	 * The constructor. Automatic dependency injection from Spring.
	 * @param strategyService The needed implementation of the IStrategyService. The service handles most of the
	 *                        logic.
	 */
	@Autowired
	public StrategyController(IStrategyService strategyService) {
		this.strategyService = strategyService;
	}

	/**
	 * Saves strategies in the database.
	 * @param request A Request object with a List filled with Strategies objects..
	 * @return Returns a Response, the statuscode depending on if the saving was successful.
	 */
	@Override
	@PutMapping(path = "")
	public ResponseEntity<Void> saveStrategies(@RequestBody PostStrategyListRequest request) {
		this.strategyService.saveStrategies(request.getStrategiesList());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Gets all strategies in the database.
	 * @return A List object filled with Strategies objects. These strategies come from the database.
	 */
	@Override
	@GetMapping(path = "")
	public ResponseEntity<List<Strategy>> getAllStrategies() {
		return new ResponseEntity<>(this.strategyService.getAllStrategies(), HttpStatus.OK);
	}


}
