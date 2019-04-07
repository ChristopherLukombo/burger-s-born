package fr.esgi.web.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.esgi.exception.BurgerSTerminalException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ResponseEntityExceptionHandler for handle exception and launch error with custom status.
 * @author christopher
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(value = { BurgerSTerminalException.class })
	protected ResponseEntity<Object> handleBurgerSTerminalException(final BurgerSTerminalException ex, final WebRequest request) {
		return handleExceptionInternal(ex, ex.getErrorMessage(), new HttpHeaders(),
				HttpStatus.valueOf(ex.getErrorCode()), request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		final Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
				Collectors.groupingBy(FieldError::getField,
						Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
				)
		);
		return new ResponseEntity(errorsMap.isEmpty() ? ex:errorsMap, headers, HttpStatus.BAD_REQUEST);
	}

}
