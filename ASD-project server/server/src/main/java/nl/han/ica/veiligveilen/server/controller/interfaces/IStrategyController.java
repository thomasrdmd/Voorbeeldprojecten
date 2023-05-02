package nl.han.ica.veiligveilen.server.controller.interfaces;

import java.util.List;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostStrategyListRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller used to save strategies to database
 */
@RequestMapping(path = "/strategy")
public interface IStrategyController {

	/**
	 * Used to save strategies to the database
	 * @param request contains strategies to save
	 * @return response containing status code
	 */
	@PutMapping(path = "")
	ResponseEntity<Void> saveStrategies(@RequestBody PostStrategyListRequest request);

	/**
	 * Used to get all strategies from the database
	 * @return list of strategies from the database.
	 */
	@GetMapping(path = "")
	ResponseEntity<List<Strategy>> getAllStrategies();
}
