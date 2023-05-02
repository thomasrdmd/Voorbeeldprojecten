package nl.han.ica.veiligveilen.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
	public InternalServerErrorException(String errorMessage) {
		super(errorMessage);
	}
}
