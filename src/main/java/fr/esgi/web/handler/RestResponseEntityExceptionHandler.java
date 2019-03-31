package fr.esgi.web.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.esgi.exception.BurgerSTerminalException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BurgerSTerminalException.class })
	protected ResponseEntity<Object> handleConflict(BurgerSTerminalException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getErrorMessage(), new HttpHeaders(),
				HttpStatus.valueOf(ex.getErrorCode()), request);
	}

}
