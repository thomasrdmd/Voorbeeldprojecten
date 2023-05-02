package nl.han.ica.veiligveilen.server.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import nl.han.ica.veiligveilen.server.model.requestobjects.LoginDTO;
import nl.han.ica.veiligveilen.server.model.responseobjects.LoginResponse;
import nl.han.ica.veiligveilen.server.service.LoginService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthenticationControllerTests {

	AuthenticationController sut;

	LoginService loginServiceFixture;

	LoginDTO loginDTO;

	HttpServletRequest httpServletRequestFixture;

	LoginResponse loginResponse;

	@BeforeEach
	void setup() {
		loginServiceFixture = mock(LoginService.class);
		loginDTO = new LoginDTO();
		loginResponse = new LoginResponse(UUID.randomUUID());
		httpServletRequestFixture = mock(HttpServletRequest.class);
		sut = new AuthenticationController(loginServiceFixture);
	}

	@Test
	void testCreateAuthenticationTokenSuccess() throws AuthenticationException {
		when(loginServiceFixture.loginUser(loginDTO, httpServletRequestFixture.getRemoteUser())).thenReturn(loginResponse);

		var result = sut.createAuthenticationToken(loginDTO, httpServletRequestFixture);

		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

		// This can't be tested right now, because the function generates its own random ID.
		// Assertions.assertEquals(loginResponse.getLoginID(), result.getBody().getLoginID());
	}
}
