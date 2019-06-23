package fr.esgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.esgi.domain.CommandCompoID;
import fr.esgi.service.dto.CommandCompoDTO;

/**
 * Service Interface for managing CommandCompoService.
 */
@Service
public interface CommandCompoService {
	
	/**
	 * Save a commandCompo.
	 *
	 * @param commandCompoDTO the entity to save
	 * @return the persisted entity
	 */
	CommandCompoDTO save(CommandCompoDTO commandCompoDTO);

	/**
	 * Get the "id" commandCompo.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	Optional<CommandCompoDTO> findOne(CommandCompoID id);

	/**
	 * Update a commandCompo.
	 *
	 * @param commandCompoDTO the entity to update
	 * @return the persisted entity
	 */
	CommandCompoDTO update(CommandCompoDTO commandCompoDTO);

	/**
	 * Get all the commandCompos.
	 *
	 * @return the list of entities
	 */
	List<CommandCompoDTO> findAll();

	/**
	 * Delete the "id" command.
	 *
	 * @param id the id of the entity
	 */
	void delete(CommandCompoID id);
}
