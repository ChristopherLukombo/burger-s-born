package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.esgi.dao.CommandCompoRepository;
import fr.esgi.domain.Command;
import fr.esgi.domain.CommandCompo;
import fr.esgi.domain.CommandCompoID;
import fr.esgi.domain.Employee;
import fr.esgi.service.dto.CommandCompoDTO;
import fr.esgi.service.impl.CommandCompoServiceImpl;
import fr.esgi.service.mapper.CommandCompoMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommandCompoServiceTest {

	private static final long ID = 1L;

	private static final String STATE = "TEST";

	@Mock
	private CommandCompoRepository commandCompoRepository;

	@Mock
	private CommandCompoMapper commandCompoMapper;

	@InjectMocks
	private CommandCompoServiceImpl commandCompoServiceImpl;

	private static CommandCompoDTO getCommandCompoDTO() {
		CommandCompoDTO commandCompoDTO = new CommandCompoDTO();
		commandCompoDTO.setId(ID);
		commandCompoDTO.setState(STATE);
		commandCompoDTO.setCommandId(ID);
		commandCompoDTO.setEmployeeId(ID);
		return commandCompoDTO;
	}

	private static CommandCompo getCommandCompo() {
		CommandCompo commandCompo = new CommandCompo();
		commandCompo.setId(ID);
		commandCompo.setState(STATE);
		Command command = getCommand();
		commandCompo.setCommand(command);
		Employee employee = getEmployee();
		commandCompo.setEmployee(employee);
		return commandCompo;
	}

	private static Employee getEmployee() {
		Employee employee = new Employee();
		employee.setId(ID);
		return employee;
	}

	private static Command getCommand() {
		Command command = new Command();
		command.setId(ID);
		return command;
	}

	@Test
	public void shouldSaveWhenIsOK() {
		// Given
		CommandCompoDTO commandCompoDTO = getCommandCompoDTO();

		// When
		when(commandCompoRepository.save(mock(CommandCompo.class))).thenReturn(getCommandCompo());
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(commandCompoDTO);

		// Then
		assertThat(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).isEqualTo(commandCompoDTO);
	}

	@Test
	public void shouldSaveWhenIsKO() {
		// Given
		CommandCompoDTO commandCompoDTO = null;

		// When
		when(commandCompoRepository.save(mock(CommandCompo.class))).thenReturn(getCommandCompo());
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(commandCompoDTO);

		// Then
		assertThat(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).isNull();
	}

	@Test
	public void shouldUpdateWhenIsOK() {
		// Given
		CommandCompoDTO commandCompoDTO = getCommandCompoDTO();

		// When
		when(commandCompoServiceImpl.update(mock(CommandCompoDTO.class))).thenReturn(commandCompoDTO);

		// Then
		assertThat(commandCompoServiceImpl.update(mock(CommandCompoDTO.class))).isEqualTo(commandCompoDTO);
	}


	@Test
	public void shouldUpdateWhenIsKO() {
		// Given
		CommandCompoDTO commandCompoDTO = null;

		// When
		when(commandCompoRepository.save(mock(CommandCompo.class))).thenReturn(getCommandCompo());
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(commandCompoDTO);

		// Then
		assertThat(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).isNull();
	}

	@Test
	public void shouldFindOneWhenIsOK() {
		// Given
		CommandCompoDTO commandCompoDTO = getCommandCompoDTO();

		CommandCompoID commandCompoID = new CommandCompoID();
		commandCompoID.setId(ID);
		commandCompoID.setCommand(getCommand());
		commandCompoID.setEmployee(getEmployee());

		// When
		when(commandCompoRepository.findById((CommandCompoID) any())).thenReturn(Optional.of(getCommandCompo()));
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(commandCompoDTO);

		// Then
		assertThat(commandCompoServiceImpl.findOne(commandCompoID)).isEqualTo(Optional.of(commandCompoDTO));
	}

	@Test
	public void shouldFindOneWhenIsKO() {
		// Given
		CommandCompoDTO commandCompoDTO = null;

		CommandCompoID commandCompoID = new CommandCompoID();
		commandCompoID.setId(ID);
		commandCompoID.setCommand(getCommand());
		commandCompoID.setEmployee(getEmployee());

		// When
		when(commandCompoRepository.findById((CommandCompoID) any())).thenReturn(Optional.empty());
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(commandCompoDTO);

		// Then
		assertThat(commandCompoServiceImpl.findOne(commandCompoID)).isEqualTo(Optional.empty());
	}

	@Test
	public void shouldFindAllWhenIsOK() {
		// Given
		List<CommandCompoDTO> commandCompoDTOs = new ArrayList<>();
		commandCompoDTOs.add(getCommandCompoDTO());

		List<CommandCompo> commandCompos = new ArrayList<>();
		commandCompos.add(getCommandCompo());

		// When
		when(commandCompoRepository.findAll()).thenReturn(commandCompos);
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(getCommandCompoDTO());

		// Then
		assertThat(commandCompoServiceImpl.findAll()).isEqualTo(commandCompoDTOs);
	}

	@Test
	public void shouldFindAllWhenIsEmpty() {
		// Given
		List<CommandCompoDTO> commandCompoDTOs = Collections.emptyList();

		List<CommandCompo> commandCompos = Collections.emptyList();

		// When
		when(commandCompoRepository.findAll()).thenReturn(commandCompos);
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(getCommandCompoDTO());

		// Then
		assertThat(commandCompoServiceImpl.findAll()).isEqualTo(commandCompoDTOs);
	}

	@Test
	public void shouldFindAllWhenIsKO() {
		// Given
		List<CommandCompo> commandCompos = null;

		// When
		when(commandCompoRepository.findAll()).thenReturn(commandCompos);
		when(commandCompoMapper.commandCompoToCommandCompoDTO(((CommandCompo) any()))).thenReturn(getCommandCompoDTO());

		// Then
		assertThatThrownBy(() -> commandCompoServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldDeleteWhenIsOK() {
		// Given
		CommandCompoID commandCompoID = new CommandCompoID();
		commandCompoID.setId(ID);
		commandCompoID.setCommand(getCommand());
		commandCompoID.setEmployee(getEmployee());

		doNothing().when(commandCompoRepository).deleteById(commandCompoID);

		// When
		commandCompoServiceImpl.delete(commandCompoID);

		// Then
		verify(commandCompoRepository, times(1)).deleteById(commandCompoID);
	}
}
