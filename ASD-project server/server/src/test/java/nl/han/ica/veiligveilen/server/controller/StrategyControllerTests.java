package nl.han.ica.veiligveilen.server.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.han.ica.veiligveilen.server.model.keys.StrategyKey;
import nl.han.ica.veiligveilen.server.model.requestobjects.PostStrategyListRequest;
import nl.han.ica.veiligveilen.server.model.dataclasses.Strategy;
import nl.han.ica.veiligveilen.server.service.interfaces.IStrategyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;


public class StrategyControllerTests {

	private StrategyController sut;
	private IStrategyService strategyServiceFixture;
	private Strategy strategy;
	private List<Strategy> strategyList;
	private PostStrategyListRequest strategyRequest;

	@BeforeEach
	void setup(){
		strategyServiceFixture = Mockito.mock(IStrategyService.class);

		StrategyKey key = new StrategyKey();
		strategy = new Strategy(key, "strategyText", Date.from(Instant.now()) ,true);

		strategyList = new ArrayList<>();
		strategyList.add(strategy);
		strategyRequest = new PostStrategyListRequest(strategyList);

		sut = new StrategyController(strategyServiceFixture);
	}

	@Test
	void testSaveStrategySuccess(){
		// Act
		var testValue = sut.saveStrategies(strategyRequest);
		// Assert
		Assertions.assertEquals(HttpStatus.OK, testValue.getStatusCode());
	}

	@Test
	void testGetAllStrategy(){
		// Arrange
		Mockito.when(strategyServiceFixture.getAllStrategies()).thenReturn(strategyList);
		// Act
		var returnValue = sut.getAllStrategies();
		// Assert
		Assertions.assertEquals(returnValue.getBody(), strategyList);
		Assertions.assertEquals(HttpStatus.OK, returnValue.getStatusCode());
	}
}
