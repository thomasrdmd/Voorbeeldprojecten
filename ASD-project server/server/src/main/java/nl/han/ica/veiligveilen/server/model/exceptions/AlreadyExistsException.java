package nl.han.ica.veiligveilen.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.RESET_CONTENT)
public class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException(String errorMessage) {
		super(errorMessage);
	}
}
