package fr.esgi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.dto.Paypal;

/**
 * Service Interface for managing PayPal.
 */
@Service
public interface PayPalService {

	/**
	 *  Create a payment.
	 *
	 * @param commandDTO command to create
	 * @return status
	 */
	Map<String, Object> createPayment(CommandDTO commandDTO);

	/**
	 * Complete a payment.
	 * @param paypal
	 * @return status
	 */
	Map<String, Object> completePayment(Paypal paypal);
	
	/**
	 * Refund a user.
	 * @param commandId
	 * @return status
	 */
	Map<String, Object> refundPayment(Long commandId);
	
}
