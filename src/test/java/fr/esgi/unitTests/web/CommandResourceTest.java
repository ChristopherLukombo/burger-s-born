package fr.esgi.unitTests.web;

import static fr.esgi.unitTests.web.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.esgi.dao.CommandRepository;
import fr.esgi.domain.Command;
import fr.esgi.service.CommandService;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.impl.CommandServiceImpl;
import fr.esgi.service.mapper.CommandMapper;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.CommandResource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommandResourceTest {

	private static final String TEST = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private CommandRepository commandRepository;

	@Mock
	private CommandMapper commandMapper;
	
	@Mock
	private MessageSource messageSource;

	@Mock
	private CommandService commandService;

	@InjectMocks
	private CommandResource commandResource;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(commandResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		commandService = new CommandServiceImpl(commandRepository, commandMapper);
		commandResource = new CommandResource(commandService, messageSource);
	}

	private static CommandDTO getCommandDTO() {
		CommandDTO commandDTO = new CommandDTO();
		commandDTO.setId(ID);
		commandDTO.setOrderStatus(TEST);
		commandDTO.setDate(ZonedDateTime.now());
		commandDTO.setPaymentId(TEST);
		commandDTO.setPrice(new BigDecimal(1));
		commandDTO.setSaleId(TEST);
		commandDTO.setCustomerId(ID);
		return commandDTO;
	}
	
	private static Command getCommand() {
		Command command = new Command();
		command.setId(ID);
		command.setOrderStatus(TEST);
		command.setDate(ZonedDateTime.now());
        command.setPaymentId(TEST);
        command.setPrice(new BigDecimal(1));
        command.setSaleId(TEST);
		return command;
	}

	@Test
	public void shouldCreateCommandWhenIsOk() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		commandDTO.setId(null);

		// When
		when(commandRepository.save(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then

		mockMvc.perform(post("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isCreated());
	}

	@Test
	public void shouldCreateCommandWhenIsKo1() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();

		// When
		when(commandRepository.save(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(post("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldCreateCommandWhenIsKo2() throws Exception {
		// Given
		CommandDTO commandDTO = null;
		Command command = null;

		// When
		when(commandRepository.save(mock(Command.class))).thenReturn(command);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(post("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldUpdateCommandWhenIsOk() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();

		// When
		when(commandRepository.saveAndFlush(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(put("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldUpdateCommandWhenIsKo1() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		commandDTO.setId(null);

		// When
		when(commandRepository.saveAndFlush(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(put("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldUpdateCommandWhenIsKo2() throws Exception {
		// Given
		CommandDTO commandDTO = null;
		Command command = null;

		// When
		when(commandRepository.saveAndFlush(mock(Command.class))).thenReturn(command);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(put("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldDeleteCommandWhenIsOk() throws Exception {
		// Given
		long userID = ID;

		// When
		doNothing().when(commandRepository).deleteById(userID);
		commandService.delete(userID);

		// Then
		mockMvc.perform(delete("/api/commands/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isNoContent());
	}

	@Test
	public void shouldGetAllCommandsWhenIsOK() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		List<Command> content = new ArrayList<Command>();
		content.add(getCommand());
		Page<Command> page = new PageImpl<>(content);

		// When
		when(commandRepository.findAllByCustomerId((org.springframework.data.domain.Pageable) any(), anyLong(), anyString())).thenReturn(page);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(get("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.param("page", "0")
				.param("size", "10")  
				.param("customerId", "1") 
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldGetAllCommandsWhenIsEmpty() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		List<Command> content = new ArrayList<Command>();
		Page<Command> page = new PageImpl<>(content);

		// When
		when(commandRepository.findAllByCustomerId((org.springframework.data.domain.Pageable) any(), anyLong(), anyString())).thenReturn(page);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		mockMvc.perform(get("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.param("page", "0")
				.param("size", "10")  
				.param("customerId", "1") 
				)
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldGetCommandWhenIsOK() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();

		// When
		when(commandRepository.findById(anyLong())).thenReturn(Optional.of(getCommand()));
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		mockMvc.perform(get("/api/commands/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldGetCommandWhenIsEmpty() throws Exception {
		// Given
		CommandDTO commandDTO = null;

		// When
		when(commandRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		mockMvc.perform(get("/api/commands/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isNotFound());
	}

}
