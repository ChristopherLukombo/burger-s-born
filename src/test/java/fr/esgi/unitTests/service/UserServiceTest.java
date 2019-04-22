package fr.esgi.unitTests.service;

import fr.esgi.dao.RoleRepository;
import fr.esgi.dao.UserRepository;
import fr.esgi.domain.User;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.service.impl.UserServiceImpl;
import fr.esgi.service.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private static final long ID = 1L;
    private static final String PSEUDO = "Ben";
    private static final String EMAIL = "ben.montreuil@gmail.com";

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void createUser() {
        user = getUser();
    }

    private static User getUser() {
        User user = new User();
        user.setId(ID);
        user.setPseudo(PSEUDO);
        user.setEmail(EMAIL);
        return user;
    }


    @Test
    public void shouldFindUserByLoginWhenIsOk() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userService.findUserByPseudo(userDTO)).thenReturn(Optional.of(user));

        // Then
        assertThat(userService.findUserByPseudo(userDTO).get().getPseudo()).isEqualTo(user.getPseudo());
    }

    @Test
    public void shouldFindUserByLoginWhenIsKO1() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userService.findUserByPseudo(userDTO)).thenReturn(Optional.of(user));

        // Then
        assertThat(userService.findUserByPseudo(userDTO).get().getPseudo()).isNotEqualTo("coucou");
    }

    @Test
    public void shouldFindUserByLoginWhenIsKO2() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userService.findUserByPseudo(userDTO)).thenReturn(Optional.of(user));

        // Then
        assertThat(userService.findUserByPseudo(userDTO).get().getPseudo()).isNotEqualTo("");
    }

    @Test
    public void shouldFindUserByEmailWhenIsOk() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userService.findUserByEmail(userDTO)).thenReturn(Optional.of(user));

        // Then
        assertThat(userService.findUserByEmail(userDTO).get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void shouldFindUserByEmailWhenIsKO1() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userService.findUserByEmail(userDTO)).thenReturn(Optional.of(user));

        // Then
        assertThat(userService.findUserByEmail(userDTO).get().getEmail()).isNotEqualTo("toto@gmail.com");
    }

    @Test
    public void shouldFindUserByEmailWhenIsKO2() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userService.findUserByEmail(userDTO)).thenReturn(Optional.of(user));

        // Then
        assertThat(userService.findUserByEmail(userDTO).get().getEmail()).isNotEqualTo("");
    }

}
