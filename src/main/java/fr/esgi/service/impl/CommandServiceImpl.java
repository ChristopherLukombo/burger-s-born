package fr.esgi.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.config.Constants;
import fr.esgi.dao.CommandRepository;
import fr.esgi.domain.Command;
import fr.esgi.service.CommandService;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.mapper.CommandMapper;

/**
 * Service Implementation for managing CommandServiceImpl.
 */
@Transactional
@Service("CommandService")
public class CommandServiceImpl implements CommandService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandServiceImpl.class);

	private final CommandRepository commandRepository;

	private final CommandMapper commandMapper;

	@Autowired
	public CommandServiceImpl(CommandRepository commandRepository, CommandMapper commandMapper) {
		this.commandRepository = commandRepository;
		this.commandMapper = commandMapper;
	}

	/**
	 * Save a command.
	 *
	 * @param commandDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public CommandDTO save(CommandDTO commandDTO) {
		LOGGER.debug("Request to save a command: {}", commandDTO);
		Command command = commandMapper.commandDTOToCommand(commandDTO);
		command = commandRepository.save(command);
		return commandMapper.commandToCommandDTO(command);
	}

	/**
	 * Get the "id" command.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	@Override
	public Optional<CommandDTO> findOne(Long id) {
		LOGGER.debug("Request to find a command by id: {}", id);
		return commandRepository.findById(id)
				.map(commandMapper::commandToCommandDTO);
	}
	
	/**
	 * Get the "paymentId" command.
	 *
	 * @param paymentId the paymentId of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	@Override
	public Optional<CommandDTO> findByPaymentId(String paymentId) {
		LOGGER.debug("Request to find a command by paymentId: {}", paymentId);
		return commandRepository.findByPaymentId(paymentId)
				.map(commandMapper::commandToCommandDTO);
	}

	/**
	 * Update a command.
	 *
	 * @param commandDTO the entity to update
	 * @return the persisted entity
	 */
	@Override
	public CommandDTO update(CommandDTO commandDTO) {
		LOGGER.debug("Request to update a command: {}", commandDTO);
		Command command = commandMapper.commandDTOToCommand(commandDTO);
		command = commandRepository.saveAndFlush(command);
		return commandMapper.commandToCommandDTO(command);
	}

	/**
	 * Get all the commands by customerId.
	 * 
	 * @param pageable
	 * @param customerId : id of the customer
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<CommandDTO> findAllByCustomerId(Pageable pageable, Long customerId) {
		LOGGER.debug("Request to find all commands by customerId: {}", customerId);
		return commandRepository.findAllByCustomerId(pageable, customerId, Constants.PAID)
				.map(commandMapper::commandToCommandDTO);
	}

	/**
	 * Delete the "id" command.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		LOGGER.debug("Request to delete a command");
		commandRepository.deleteById(id);
	}
}
