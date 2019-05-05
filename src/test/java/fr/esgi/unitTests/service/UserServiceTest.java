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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
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
    private UserServiceImpl userServiceImpl;

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
        Optional<User> user = Optional.of(this.user);

        // When
        when(userServiceImpl.findUserByPseudo(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByPseudo(mock(UserDTO.class)).get().getPseudo()).isEqualTo(this.user.getPseudo());
    }

    @Test
    public void shouldFindUserByLoginWhenIsKO1() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userServiceImpl.findUserByPseudo(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByPseudo(mock(UserDTO.class)).get().getPseudo()).isNotEqualTo("BIBI");
    }

    @Test
    public void shouldFindUserByLoginWhenIsKO2() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userServiceImpl.findUserByPseudo(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByPseudo(mock(UserDTO.class)).get().getPseudo()).isNotEqualTo("");
    }

    @Test
    public void shouldFindUserByLoginWhenIsKO3() {
        // Given
        Optional<User> user = Optional.ofNullable(null);

        // When
        when(userServiceImpl.findUserByPseudo(mock(UserDTO.class))).thenReturn(user);

        System.out.println(userServiceImpl.findUserByPseudo(mock(UserDTO.class)));

        // Then
        assertThat(userServiceImpl.findUserByPseudo(mock(UserDTO.class))).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldFindUserByEmailWhenIsOk() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userServiceImpl.findUserByEmail(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByEmail(mock(UserDTO.class)).get().getEmail()).isEqualTo(this.user.getEmail());
    }

    @Test
    public void shouldFindUserByEmailWhenIsKO1() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userServiceImpl.findUserByEmail(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByEmail(mock(UserDTO.class)).get().getEmail()).isNotEqualTo("toto@gmail.com");
    }

    @Test
    public void shouldFindUserByEmailWhenIsKO2() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userServiceImpl.findUserByEmail(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByEmail(mock(UserDTO.class)).get().getEmail()).isNotEqualTo("");
    }

    @Test
    public void shouldFindUserByEmailWhenIsKO3() {
        // Given
        Optional<User> user = Optional.ofNullable(null);

        // When
        when(userServiceImpl.findUserByEmail(mock(UserDTO.class))).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByEmail(mock(UserDTO.class))).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldRegisterWhenIsOk() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(userServiceImpl.registerUser(mock(UserDTO.class), anyString())).thenReturn(userDTO);

        // Then
        assertThat(userServiceImpl.registerUser(mock(UserDTO.class), anyString())).isEqualTo(userDTO);
    }

    @Test
    public void shouldRegisterWhenIsKo() {
        // Given
        UserDTO userDTO = null;

        // When
        when(userServiceImpl.registerUser(mock(UserDTO.class), anyString())).thenReturn(userDTO);

        // Then
        assertThat(userServiceImpl.registerUser(mock(UserDTO.class), anyString())).isEqualTo(null);
    }
}
