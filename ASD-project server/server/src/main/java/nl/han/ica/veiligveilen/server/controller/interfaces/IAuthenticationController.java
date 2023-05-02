package nl.han.ica.veiligveilen.server.controller.interfaces;

import javax.servlet.http.HttpServletRequest;
import nl.han.ica.veiligveilen.server.model.requestobjects.LoginDTO;
import nl.han.ica.veiligveilen.server.model.responseobjects.LoginResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling authentication
 */
@RequestMapping(path = "/authentication")
public interface IAuthenticationController {

	/**
	 * Creates an authentication token.
	 * @param authenticationRequest object containing login information.
	 * @param request The http request to authenticate on.
	 * @return Response containing the status code and the authentication token.
	 */
	@PostMapping(value = "/authenticate")
	ResponseEntity<LoginResponse> createAuthenticationToken
		(@RequestBody LoginDTO authenticationRequest, HttpServletRequest request) throws AuthenticationException;
}
