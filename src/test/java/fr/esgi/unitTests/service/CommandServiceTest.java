package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import fr.esgi.dao.CommandRepository;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.impl.CommandServiceImpl;
import fr.esgi.service.mapper.CommandMapper;

@ActiveProfiles("test")
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
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	    commandServiceImpl = new CommandServiceImpl(commandRepository, commandMapper);
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
	public void shouldSaveWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		
		// When
		when(commandServiceImpl.save(mock(CommandDTO.class))).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.save(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldSaveWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;
		
		// When
		when(commandServiceImpl.save(mock(CommandDTO.class))).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.save(mock(CommandDTO.class))).isNull();
	}

	// TODO : trouver le moyen de mocker un optional
//	@Test
//	public void shouldFindOneWhenIsOK() {
//		// Given
//		CommandDTO commandDTO = getCommandDTO();
//		
//		// When
//		when(commandServiceImpl.findOne(anyLong())).thenReturn(Optional.ofNullable(commandDTO));
//		
//		// Then
//		assertThat(commandServiceImpl.findOne(anyLong())).isEqualTo(Optional.ofNullable(commandDTO));
//	}
//
//	@Test
//	public void shouldFindOneWhenIsKO() {
//		// Given
//		CommandDTO commandDTO = null;
//		
//		// When
//		when(commandServiceImpl.findOne(anyLong())).thenReturn(Optional.ofNullable(commandDTO));
//		
//		// Then
//		assertThat(commandServiceImpl.findOne(anyLong())).isEqualTo(Optional.ofNullable(commandDTO));
//	}
	
	@Test
	public void shouldUpdateWhenIsOK() {
		// Given
		CommandDTO commandDTO = getCommandDTO();
		
		// When
		when(commandServiceImpl.update(mock(CommandDTO.class))).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.update(mock(CommandDTO.class))).isEqualTo(commandDTO);
	}
	
	@Test
	public void shouldUpdateWhenIsKO() {
		// Given
		CommandDTO commandDTO = null;
		
		// When
		when(commandServiceImpl.update(mock(CommandDTO.class))).thenReturn(commandDTO);
		
		// Then
		assertThat(commandServiceImpl.update(mock(CommandDTO.class))).isEqualTo(commandDTO);
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
	
}
