package fr.esgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CommandDTO;

/**
 * Service Interface for managing CommandService.
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
	 * Get all the commands.
	 *
	 * @return the list of entities
	 */
	List<CommandDTO> findAll();

	/**
	 * Delete the "id" command.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
