package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.esgi.dao.ManagerRepository;
import fr.esgi.domain.Manager;
import fr.esgi.domain.User;
import fr.esgi.service.impl.ManagerServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ManagerServiceTest {

	private static final long ID = 1L;
	private static final String PSEUDO = "Ben";
	private static final String EMAIL = "ben.montreuil@gmail.com";

	@Mock
	private ManagerRepository managerRepository;

	@InjectMocks
	private ManagerServiceImpl managerServiceImpl;

	private static Manager getManager() {
		Manager manager = new Manager();
		manager.setId(1L);
		manager.setUser(getUser());
		return manager;
	}

	private static User getUser() {
		User user = new User();
		user.setId(ID);
		user.setPseudo(PSEUDO);
		user.setEmail(EMAIL);
		return user;
	}
	
	@Test
	public void shouldFindByUserNameWhenIsOK() {
		// Given
		Manager manager = getManager();

		// When
		when(managerRepository.findByUserName(anyString())).thenReturn(Optional.ofNullable(manager));

		// Then
		assertThat(managerServiceImpl.findByUserName(PSEUDO)).isEqualTo(manager.getId());
	}
	
	@Test
	public void shouldFindByUserNameWhenIsKO() {
		// Given
		Manager manager = null;

		// When
		when(managerRepository.findByUserName(anyString())).thenReturn(Optional.ofNullable(manager));

		// Then
		assertThat(managerServiceImpl.findByUserName(PSEUDO)).isEqualTo(null);
	}
}
