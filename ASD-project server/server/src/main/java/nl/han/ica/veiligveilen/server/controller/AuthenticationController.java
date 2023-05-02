package nl.han.ica.veiligveilen.server.controller;

import javax.servlet.http.HttpServletRequest;
import nl.han.ica.veiligveilen.server.controller.interfaces.IAuthenticationController;
import nl.han.ica.veiligveilen.server.model.requestobjects.LoginDTO;
import nl.han.ica.veiligveilen.server.model.responseobjects.LoginResponse;
import nl.han.ica.veiligveilen.server.service.LoginService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expose a POST API /authenticate using the AuthenticationController.
 * The POST API gets username and password in the body.
 * Using Spring Authentication Manager the username and password get authenticated.
 * If the credentials are valid, a JWT token is created using the JWTTokenUtil and provided to the client.
 */
@RestController
@RequestMapping(path = "/authentication")
public class AuthenticationController implements IAuthenticationController {

	private final LoginService loginService;

	@Autowired
	public AuthenticationController(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * Method used to handle post requests at /authenticate
	 * @param authenticationRequest data of the requester, contains a username, password and user id
	 * @param request the actual send request
	 * @return it returns a response depending on whether the request is valid or not.
	 * 		If it's valid a token gets returned, otherwise an HTTP status code gets send back informing
	 * 		the user that the given information is not valid.
	 */
	@PostMapping(value = "/authenticate")
	public ResponseEntity<LoginResponse> createAuthenticationToken
	(@RequestBody LoginDTO authenticationRequest, HttpServletRequest request) throws AuthenticationException {
		return ResponseEntity.ok(
			this.loginService.loginUser(authenticationRequest, request.getRemoteAddr())
		);
	}


}
