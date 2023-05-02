package nl.han.ica.veiligveilen.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoMasterFoundException extends RuntimeException {
	public NoMasterFoundException(String errorMessage) {
		super(errorMessage);
	}
}
