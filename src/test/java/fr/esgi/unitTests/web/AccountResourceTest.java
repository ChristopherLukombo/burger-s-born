package fr.esgi.unitTests.web;

import fr.esgi.dao.RoleRepository;
import fr.esgi.dao.UserRepository;
import fr.esgi.domain.User;
import fr.esgi.service.UserService;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.service.impl.UserServiceImpl;
import fr.esgi.service.mapper.UserMapper;
import fr.esgi.web.ManagedUser;
import fr.esgi.web.rest.AccountResource;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static fr.esgi.unitTests.web.TestUtil.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class AccountResourceTest {

    private static final long ID = 1L;
    private static final String PSEUDO = "Ben";
    private static final String EMAIL = "ben.montreuil@gmail.com";
    private static final String PASSWORD = "benenede";

    private MockMvc mockMvc;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AccountResource accountResource;

    private User user;

    private static final String IMAGES_FOLDER = "./images";

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        initMocks();
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountResource)
                .build();
    }

    private void initMocks() {
        userService = new UserServiceImpl(passwordEncoder, userRepository, roleRepository, userMapper);
        ReflectionTestUtils.setField(userService, "imagesDirectory", ".");
        accountResource = new AccountResource(userService, messageSource);
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
    public void shouldRegisterWhenIsOk() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        ManagedUser managedUser = new ManagedUser();
        managedUser.setPseudo(PSEUDO);
        managedUser.setEmail(EMAIL);
        managedUser.setPassword(PASSWORD);

        // When
        when(userService.registerUser(mock(UserDTO.class), anyString())).thenReturn(userDTO);
        mockMvc.perform(post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(managedUser)))
                .andExpect(status().isCreated());

        // Then
        assertThat(userService.registerUser(mock(UserDTO.class), anyString())).isEqualTo(userDTO);
    }

    @Test
    public void shouldRegisterWhenIsKo() throws Exception {
        // Given
        UserDTO userDTO = null;

        ManagedUser managedUser = null;

        // When
        when(userService.registerUser(mock(UserDTO.class), anyString())).thenReturn(null);
        mockMvc.perform(post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(managedUser)))
                .andExpect(status().isBadRequest());

        // Then
        assertThat(userService.registerUser(mock(UserDTO.class), anyString())).isEqualTo(userDTO);
    }

    @Test
    public void shouldRegisterWithFileWhenIsOk() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

        // When
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/register/file/1")
                .file(file))
                .andExpect(status().isOk());

        // Then
        assertThat(userRepository.findById(anyLong()).get().getImageUrl()).isNotNull();
    }

    @Before
    @After
    public void deleteFolder() throws IOException {
        if (Files.isDirectory(Paths.get(IMAGES_FOLDER))) {
            deleteFolderAndContents();
        }
    }

    public void deleteFolderAndContents() throws IOException {
        List<String> fileNames = Arrays.asList(IMAGES_FOLDER);

        for (String fileName: fileNames) {
            FileUtils.forceDelete(new File(fileName));
        }
    }
}
