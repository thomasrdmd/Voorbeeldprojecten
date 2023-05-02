package nl.han.ica.veiligveilen.server.service.interfaces;

import nl.han.ica.veiligveilen.server.model.requestobjects.LoginDTO;
import nl.han.ica.veiligveilen.server.model.responseobjects.LoginResponse;
import org.apache.tomcat.websocket.AuthenticationException;

public interface ILoginService {
	LoginResponse loginUser(LoginDTO authenticationRequest, String ip) throws AuthenticationException;
}
