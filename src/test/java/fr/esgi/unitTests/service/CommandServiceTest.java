package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import fr.esgi.dao.CommandRepository;
import fr.esgi.domain.Command;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.impl.CommandServiceImpl;
import fr.esgi.service.mapper.CommandMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommandServiceTest {
	
	private static final String TEST = "TEST";

	private static final long ID = 1L;

	@Mock
	private CommandRepository commandRepository;

	@Mock
	private CommandMapper commandMapper;
	
	@InjectMocks
	private CommandServiceImpl commandServiceImpl;
	
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
	public void shouldSaveWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		
		// When
		when(commandRepository.save(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.save(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldSaveWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;
		
		// When
		when(commandRepository.save(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.save(commandDTO)).isNull();
	}

	@Test
	public void shouldFindOneWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		
		// When
		when(commandRepository.findById(anyLong())).thenReturn(Optional.of(getCommand()));
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.findOne(anyLong())).isEqualTo(Optional.ofNullable(commandDTO));
	}

	@Test
	public void shouldFindOneWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;

		// When
		when(commandRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		assertThat(commandServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(commandDTO));
	}
	
	@Test
	public void shouldFindByPaymentIdWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();

		// When
		when(commandRepository.findByPaymentId(anyString())).thenReturn(Optional.ofNullable(getCommand()));
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		assertThat(commandServiceImpl.findByPaymentId(TEST)).isEqualTo(Optional.ofNullable(commandDTO));
	}
	
	@Test
	public void shouldFindByPaymentIdWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;

		// When
		when(commandRepository.findByPaymentId(anyString())).thenReturn(Optional.empty());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		assertThat(commandServiceImpl.findByPaymentId(anyString())).isEqualTo(Optional.ofNullable(commandDTO));
	}
	
	@Test
	public void shouldUpdateWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		
		// When
		when(commandRepository.saveAndFlush(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.update(commandDTO)).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldUpdateWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;
		
		// When
		when(commandRepository.saveAndFlush(mock(Command.class))).thenReturn(getCommand());
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.update(commandDTO)).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldDeleteWhenIsOK() {
		// Given
		doNothing().when(commandRepository).deleteById(anyLong());
		
		// When
		commandServiceImpl.delete(anyLong());
		
		// Then
		verify(commandRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	public void shouldFindAllByCustomerIdWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		List<Command> content = new ArrayList<Command>();
		content.add(getCommand());
		Page<Command> page = new PageImpl<>(content);

		// When
		when(commandRepository.findAllByCustomerId((org.springframework.data.domain.Pageable) any(), anyLong(), anyString())).thenReturn(page);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		assertThat(commandServiceImpl.findAllByCustomerId(PageRequest.of(0, 10), ID)).isNotEmpty();
	}
	
	@Test
	public void shouldFindAllByCustomerIdWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;
		Page<Command> page = null;

		// When
		when(commandRepository.findAllByCustomerId((org.springframework.data.domain.Pageable) any(), anyLong(), anyString())).thenReturn(page);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		assertThatThrownBy(() -> commandServiceImpl.findAllByCustomerId(PageRequest.of(0, 10), ID))
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldFindAllByCustomerIdWhenIsEmpty() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		List<Command> content = new ArrayList<Command>();
		Page<Command> page = new PageImpl<>(content);

		// When
		when(commandRepository.findAllByCustomerId((org.springframework.data.domain.Pageable) any(), anyLong(), anyString())).thenReturn(page);
		when(commandMapper.commandToCommandDTO((Command) any())).thenReturn(commandDTO);

		// Then
		assertThat(commandServiceImpl.findAllByCustomerId(PageRequest.of(0, 10), ID)).isEmpty();
	}
	
}
