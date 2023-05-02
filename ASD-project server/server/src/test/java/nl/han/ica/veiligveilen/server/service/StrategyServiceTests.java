package nl.han.ica.veiligveilen.server.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.han.ica.veiligveilen.server.dao.IStrategyRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Login;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;
import nl.han.ica.veiligveilen.server.model.keys.StrategyKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StrategyServiceTests {

	private StrategyService sut;
	private IStrategyRepository strategyRepositoryFixture;

	private List<Strategy> strategiesList;

	@BeforeEach
	void setup(){
		strategyRepositoryFixture = Mockito.mock(IStrategyRepository.class);

		sut = new StrategyService(strategyRepositoryFixture);
		strategiesList = new ArrayList<>();
		strategiesList.add(new Strategy(
			new StrategyKey(new Lot(), new Login()),
			"strategyText", Date.from(Instant.now()),true));
	}

	@Test
	void testSaveStrategySuccess(){
		// Arrange

		// Act
		sut.saveStrategies(strategiesList);
		// Assert
		verify(strategyRepositoryFixture).saveAll(strategiesList);

	}

	@Test
	void testGetAllStrategiesSuccess() {
		when(strategyRepositoryFixture.findAll()).thenReturn(strategiesList);

		var result = sut.getAllStrategies();

		Assertions.assertEquals(result, strategiesList);
	}


}
