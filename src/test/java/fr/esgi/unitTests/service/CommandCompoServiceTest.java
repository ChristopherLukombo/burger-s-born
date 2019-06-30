package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import fr.esgi.dao.CommandCompoRepository;
import fr.esgi.domain.CommandCompo;
import fr.esgi.service.dto.CommandCompoDTO;
import fr.esgi.service.impl.CommandCompoServiceImpl;
import fr.esgi.service.mapper.CommandCompoMapper;

@ActiveProfiles("test")
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
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		commandCompoServiceImpl = new CommandCompoServiceImpl(commandCompoRepository, commandCompoMapper);
	}
	
	private static CommandCompoDTO getCommandCompoDTO() {
		CommandCompoDTO commandCompoDTO = new CommandCompoDTO();
		commandCompoDTO.setId(ID);
		commandCompoDTO.setState(STATE);
		commandCompoDTO.setCommandId(ID);
		commandCompoDTO.setEmployeeId(ID);
		return commandCompoDTO;
	}
	
	@Test
	public void shouldSaveWhenIsOK() {
		// Given
		CommandCompoDTO commandCompoDTO = getCommandCompoDTO();
		
		// When
		when(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).thenReturn(commandCompoDTO);
		
		// Then
		assertThat(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).isEqualTo(commandCompoDTO);
	}
	
	@Test
	public void shouldSaveWhenIsKO() {
		// Given
		CommandCompoDTO commandCompoDTO = null;
		
		// When
		when(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).thenReturn(commandCompoDTO);
		
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
		when(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).thenReturn(commandCompoDTO);
		
		// Then
		assertThat(commandCompoServiceImpl.save(mock(CommandCompoDTO.class))).isNull();
	}

}
