package fr.esgi.web.rest;

import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.PayPalService;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.dto.Paypal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing Paypal payment.
 */
@Api(value = "PayPal")
@RestController
@RequestMapping("/api")
public class PayPalResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayPalResource.class);

	private final PayPalService payPalService;

	private final MessageSource messageSource;

	@Autowired
	public PayPalResource(PayPalService payPalService, MessageSource messageSource) {
		this.payPalService = payPalService;
		this.messageSource = messageSource;
	}

	/**
	 * POST  /make/payment : make a payment.
	 * 
	 * @param commandDTO
	 * @return
	 * @throws BurgerSTerminalException 
	 */
	@ApiOperation(value = "Make a payment.")
	@PostMapping("/make/payment")
	public ResponseEntity<Map<String, Object>> makePayment(@RequestBody CommandDTO commandDTO, Locale locale) throws BurgerSTerminalException {
		LOGGER.debug("REST request to create a command: {}", commandDTO);
		Map<String, Object> creationPayment = payPalService.createPayment(commandDTO);
		if (null == creationPayment) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PAYMENT_CREATION, null, locale));
		}
		return ResponseEntity.ok().body(creationPayment);
	}

	/**
	 * POST  /complete/payment : complete a payment.
	 * 
	 * @param paypal
	 * @return
	 * @throws BurgerSTerminalException 
	 */
	@ApiOperation(value = "Complete a payment.")
	@PostMapping("/complete/payment")
	public ResponseEntity<String> completePayment(@RequestBody @Valid Paypal paypal, Locale locale) throws BurgerSTerminalException {
		Map<String, Object> completedPayment = payPalService.completePayment(paypal);
		if (null == completedPayment) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PAYMENT_VALIDATION, null, locale));
		}
		return ResponseEntity.ok().body(completedPayment.toString());
	}

	/**
	 * POST  /refund/payment : refund a user.
	 * 
	 * @param commandId
	 * @return
	 * @throws BurgerSTerminalException 
	 */
	@ApiOperation(value = "Refund a user.")
	@PostMapping("/refund/payment/{commandId}")
	public ResponseEntity<Object> refundPayment(@PathVariable Long commandId, Locale locale) throws BurgerSTerminalException {
		Map<String, Object> refundPayment = payPalService.refundPayment(commandId);
		if (null == refundPayment) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PAYMENT_REFUND, null, locale));
		}
		return ResponseEntity.ok().build();
	}

}
