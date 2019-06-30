package fr.esgi.unitTests.web;

import static fr.esgi.unitTests.web.TestUtil.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CommandResourceTest {

	private static final String TEST = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private CommandRepository commandRepository;

	@Mock
	private CommandMapper commandMapper;

	@Mock
	private CommandService commandService;

	@InjectMocks
	private CommandResource commandResource;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(commandResource).setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		commandService = new CommandServiceImpl(commandRepository, commandMapper);
		commandResource = new CommandResource(commandService);
	}

	private static CommandDTO getCommandDTO() {
		CommandDTO commandDTO = new CommandDTO();
		commandDTO.setId(ID);
		commandDTO.setOrderStatus(TEST);
		commandDTO.setDate(ZonedDateTime.now());
		commandDTO.setPaymentId(TEST);
		commandDTO.setPrice(new BigDecimal(1));
		commandDTO.setSaleId(TEST);
		return commandDTO;
	}

	@Test
	public void shouldCreateCommandWhenIsOk() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		commandDTO.setId(null);

		// When
		when(commandService.save(mock(CommandDTO.class))).thenReturn(commandDTO);
		mockMvc.perform(post("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isCreated());

		// Then
		assertThat(commandService.save(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldCreateCommandWhenIsKo1() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();

		// When
		when(commandService.save(mock(CommandDTO.class))).thenReturn(commandDTO);
		mockMvc.perform(post("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());

		// Then
		assertThat(commandService.save(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldCreateCommandWhenIsKo2() throws Exception {
		// Given
		CommandDTO commandDTO = null;

		// When
		when(commandService.save(mock(CommandDTO.class))).thenReturn(commandDTO);
		mockMvc.perform(post("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());

		// Then
		assertThat(commandService.save(mock(CommandDTO.class))).isNull();
	}
	
	@Test
	public void shouldUpdateCommandWhenIsOk() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();

		// When
		when(commandService.update(mock(CommandDTO.class))).thenReturn(commandDTO);
		mockMvc.perform(put("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isOk());

		// Then
		assertThat(commandService.update(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldUpdateCommandWhenIsKo1() throws Exception {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		commandDTO.setId(null);

		// When
		when(commandService.update(mock(CommandDTO.class))).thenReturn(commandDTO);
		mockMvc.perform(put("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());

		// Then
		assertThat(commandService.update(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldUpdateCommandWhenIsKo2() throws Exception {
		// Given
		CommandDTO commandDTO = null;

		// When
		when(commandService.update(mock(CommandDTO.class))).thenReturn(commandDTO);
		mockMvc.perform(put("/api/commands")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isBadRequest());

		// Then
		assertThat(commandService.update(mock(CommandDTO.class))).isNull();
	}
	
	@Test
	public void shouldDeleteCommandWhenIsOk() throws Exception {
		// When
		doNothing().when(commandRepository).deleteById(anyLong());
		commandService.delete(anyLong());
		
		mockMvc.perform(delete("/api/commands/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isNoContent());
	}

}
