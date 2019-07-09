package fr.esgi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.CommandCompoRepository;
import fr.esgi.domain.CommandCompo;
import fr.esgi.domain.CommandCompoID;
import fr.esgi.service.CommandCompoService;
import fr.esgi.service.dto.CommandCompoDTO;
import fr.esgi.service.mapper.CommandCompoMapper;

/**
 * Service Implementation for managing CommandCompo.
 */
@Service("CommandCompoService")
@Transactional
public class CommandCompoServiceImpl implements CommandCompoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandCompoServiceImpl.class);

	private final CommandCompoRepository commandCompoRepository;

	private final CommandCompoMapper commandCompoMapper;

	@Autowired
	public CommandCompoServiceImpl(CommandCompoRepository commandCompoRepository,
			CommandCompoMapper commandCompoMapper) {
		this.commandCompoRepository = commandCompoRepository;
		this.commandCompoMapper = commandCompoMapper;
	}

	/**
	 * Save a commandCompo.
	 *
	 * @param commandCompoDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public CommandCompoDTO save(CommandCompoDTO commandCompoDTO) {
		LOGGER.debug("Request to save a commandCompo: {}", commandCompoDTO);
		CommandCompo commandCompo = commandCompoMapper.commandCompoDTOToCommandCompo(commandCompoDTO);
		commandCompo = commandCompoRepository.save(commandCompo);
		return commandCompoMapper.commandCompoToCommandCompoDTO(commandCompo);
	}

	/**
	 * Get the "id" commandCompo.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	@Override
	public Optional<CommandCompoDTO> findOne(CommandCompoID id) {
		LOGGER.debug("Request to find a commandCompo by id: {}", id);
		return commandCompoRepository.findById(id)
				.map(commandCompoMapper::commandCompoToCommandCompoDTO);
	}

	/**
	 * Update a commandCompo.
	 *
	 * @param commandCompoDTO the entity to update
	 * @return the persisted entity
	 */
	@Override
	public CommandCompoDTO update(CommandCompoDTO commandCompoDTO) {
		LOGGER.debug("Request to update a commandCompo: {}", commandCompoDTO);
		CommandCompo commandCompo = commandCompoMapper.commandCompoDTOToCommandCompo(commandCompoDTO);
		commandCompo = commandCompoRepository.saveAndFlush(commandCompo);
		return commandCompoMapper.commandCompoToCommandCompoDTO(commandCompo);
	}

	/**
	 * Get all the commandCompos.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	@Override
	public List<CommandCompoDTO> findAll() {
		LOGGER.debug("Request to find all commandCompos");
		return commandCompoRepository.findAll().stream()
				.map(commandCompoMapper::commandCompoToCommandCompoDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Delete the "id" command.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(CommandCompoID id) {
		LOGGER.debug("Request to delete a command");
		commandCompoRepository.deleteById(id);	
	}

}
