package nl.han.ica.veiligveilen.server.model.requestobjects;

import java.util.List;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;

public class PostStrategyListRequest {
	private List<Strategy> strategiesList;

	public PostStrategyListRequest() {
	}

	public PostStrategyListRequest(List<Strategy> strategiesList) {
		this.strategiesList = strategiesList;
	}

	public List<Strategy> getStrategiesList() {
		return strategiesList;
	}

	public void setStrategiesList(List<Strategy> strategiesList) {
		this.strategiesList = strategiesList;
	}
}
