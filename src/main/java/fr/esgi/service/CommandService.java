package fr.esgi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CommandDTO;

/**
 * Service Interface for managing Command.
 */
@Service
public interface CommandService {

	/**
	 * Save a command.
	 *
	 * @param commandDTO the entity to save
	 * @return the persisted entity
	 */
	CommandDTO save(CommandDTO commandDTO);

	/**
	 * Get the "id" command.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	Optional<CommandDTO> findOne(Long id);
	
	/**
	 * Get the "paymentId" command.
	 *
	 * @param paymentId the paymentId of the entity
	 * @return the entity
	 */
	Optional<CommandDTO> findByPaymentId(String paymentId);

	/**
	 * Update a command.
	 *
	 * @param commandDTO the entity to update
	 * @return the persisted entity
	 */
	CommandDTO update(CommandDTO commandDTO);

	/**
	 * Get all the commands by customerId.
	 * @param customerId 
	 *
	 * @return the page of entities
	 */
	Page<CommandDTO> findAllByCustomerId(Pageable pageable, Long customerId);

	/**
	 * Delete the "id" command.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
