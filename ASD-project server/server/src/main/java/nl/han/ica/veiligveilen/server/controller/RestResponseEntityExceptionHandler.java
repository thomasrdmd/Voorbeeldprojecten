package nl.han.ica.veiligveilen.server.controller;

import nl.han.ica.veiligveilen.server.model.exceptions.AlreadyExistsException;
import nl.han.ica.veiligveilen.server.model.exceptions.InternalServerErrorException;
import nl.han.ica.veiligveilen.server.model.exceptions.NoMasterFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InternalServerErrorException.class)
	protected ResponseEntity<Object> handleInternalServerError (Exception ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();

		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(NoMasterFoundException.class)
	protected ResponseEntity<Object> handleNoMaster (Exception ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();

		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.NO_CONTENT, request);
	}

	@ExceptionHandler(AlreadyExistsException.class)
	protected ResponseEntity<Object> handleAlreadyExists (Exception ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();

		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.RESET_CONTENT, request);
	}
}
