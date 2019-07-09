package fr.esgi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CustomerDTO;

/**
 * Service Interface for managing Customer.
 */
@Service
public interface CustomerService {

	/**
	 * Get the customer by id.
	 * 
	 * @param id the id of customer
	 * @return the entity persisted
	 */
	Optional<CustomerDTO> findOne(Long id);

	/**
	 * Get the customer by userName.
	 * 
	 * @param userName
	 * @return the id of customer
	 */
	Long findByUserName(String userName);
}