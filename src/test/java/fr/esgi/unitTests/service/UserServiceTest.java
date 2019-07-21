package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.esgi.config.ConfigurationService;
import fr.esgi.dao.CustomerRepository;
import fr.esgi.dao.RoleRepository;
import fr.esgi.dao.UserRepository;
import fr.esgi.domain.Customer;
import fr.esgi.domain.Role;
import fr.esgi.domain.User;
import fr.esgi.enums.RoleName;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.service.impl.UserServiceImpl;
import fr.esgi.service.mapper.UserMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
	
	private static final String NULL = "null";
	private static final String IMAGES_FOLDER = "./images";
    private static final String IMAGE_DIRECTORY = ".";
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
    
    @Mock
    private ConfigurationService configurationService;
    
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;

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
    
    private static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);
        return userDTO;
    }
    
	private static Role getRole() {
		Role role = new Role();
        role.setId(ID);
        role.setName(RoleName.ROLE_CUSTOMER.name());
		return role;
	}

    @Test
    public void shouldFindUserByLoginWhenIsOk() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByPseudo(getUserDTO())).isNotEqualTo(Optional.empty());
    }

    @Test
    public void shouldFindUserByLoginWhenIsKO() {
        // Given
        Optional<User> user = Optional.empty();

        // When
        when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByPseudo(getUserDTO())).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldFindUserByEmailWhenIsOK() {
        // Given
        Optional<User> user = Optional.of(this.user);

        // When
        when(userRepository.findOneByEmailIgnoreCase(anyString())).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByEmail(getUserDTO())).isNotEqualTo(Optional.empty());
    }

    @Test
    public void shouldFindUserByEmailWhenIsKO() {
        // Given
        Optional<User> user = Optional.empty();

        // When
        when(userRepository.findOneByEmailIgnoreCase(anyString())).thenReturn(user);

        // Then
        assertThat(userServiceImpl.findUserByPseudo(getUserDTO())).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldRegisterWhenIsOK() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);
        userDTO.setRoleId(ID);
        Role role = getRole();

        // When
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(userRepository.save(mock(User.class))).thenReturn(user);
    	when(customerRepository.save((Customer) any())).thenReturn(new Customer());
        when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);
        
        // Then
        assertThat(userServiceImpl.registerUser(userDTO, "Totobibi24!")).isEqualTo(userDTO);
    }

    @Test
    public void shouldRegisterWhenUserHasNoRoleIsOK() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setPseudo(PSEUDO);
        userDTO.setEmail(EMAIL);

        // When
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.save((User) any())).thenReturn(user);
        when(customerRepository.save((Customer) any())).thenReturn(new Customer());
        when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);
        
        // Then
        assertThat(userServiceImpl.registerUser(userDTO, "Totobibi24!")).isEqualTo(userDTO);
    }

    @Test
    public void shouldRegisterWhenIsKO() {
        // Given
        UserDTO userDTO = null;

        // When
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.save(mock(User.class))).thenReturn(null);
        when(customerRepository.save((Customer) any())).thenReturn(null);
        when(userMapper.userToUserDTO((User) any())).thenReturn(null);

        // Then
    	assertThatThrownBy(() -> userServiceImpl.registerUser(userDTO, "Totobibi24!"))
		.isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void shouldGetImageURLWhenIsOK() throws BurgerSTerminalException, IOException {
    	// Given
    	String imageDirectory = IMAGE_DIRECTORY;
    	User user = this.user;
    	user.setImageUrl("filename.txt");
    	
    	// When
    	when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
    	when(configurationService.getImagesDirectory()).thenReturn(imageDirectory);
    	createFoldersAndFile();
    	
    	// Then
    	assertThat(userServiceImpl.getImageURL(user.getPseudo())).isNotNull();
    }
    
    
    @Test
    public void shouldGetImageURLWhenUserNotExists() throws BurgerSTerminalException, IOException {
    	// Given
    	String imageDirectory = IMAGE_DIRECTORY;
    	User user = this.user;
    	user.setImageUrl("filename.txt");
    	
    	// When
    	when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.empty());
    	when(configurationService.getImagesDirectory()).thenReturn(imageDirectory);
    	createFoldersAndFile();
    	
    	// Then
    	assertThat(userServiceImpl.getImageURL(user.getPseudo())).isNotNull();
    }
    
    @Test
    public void shouldGetImageURLWhenFileNotExists() throws BurgerSTerminalException, IOException {
    	// Given
    	String imageDirectory = ".";
    	User user = this.user;
    	user.setImageUrl("tst.txt");
    	
    	// When
    	when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
    	when(configurationService.getImagesDirectory()).thenReturn(imageDirectory);
    	
    	// Then
    	assertThatThrownBy(() -> userServiceImpl.getImageURL(user.getPseudo()))
		.isInstanceOf(BurgerSTerminalException.class);
    }
    
    @Test
    public void shouldStoreWhenIsOK() throws BurgerSTerminalException, IOException {
    	// Given
    	MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

    	// When
    	when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
    	when(userRepository.saveAndFlush((User) any())).thenReturn(user);
    	when(configurationService.getImagesDirectory()).thenReturn(".");
    	userServiceImpl.store(file, user.getId());

    	// Then
    	verify(userRepository, times(1)).findById(anyLong());
    	verify(configurationService, atLeast(1)).getImagesDirectory();
    	verify(userRepository, atLeast(1)).saveAndFlush((User) any());
    	
    }
    
    @Test
    public void shouldStoreWhenUserNotExists() throws BurgerSTerminalException {
    	// Given
    	MockMultipartFile file = null;

    	// When
    	when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    	when(userRepository.saveAndFlush(mock(User.class))).thenReturn(null);
    	when(configurationService.getImagesDirectory()).thenReturn(null);
    	userServiceImpl.store(file, user.getId());
    	
    	// Then
    	verify(userRepository, times(1)).findById(anyLong());
    }

	private void createFoldersAndFile() throws IOException {
		Files.createDirectory(Paths.get("./images"));
		Files.createDirectory(Paths.get("./images/1"));
		File file = new File("./images/1/filename.txt");
		file.createNewFile();
	}
    
    @Before
    @After
    public void deleteFolder() throws IOException {
        if (Files.isDirectory(Paths.get(IMAGES_FOLDER))) {
            deleteFolderAndContents();
        }
    }

    public void deleteFolderAndContents() throws IOException {
        List<String> fileNames = Arrays.asList(IMAGES_FOLDER, NULL);

        for (String fileName: fileNames) {
        	File file = new File(fileName);
        	if (file.exists()) {
        		FileUtils.forceDelete(new File(fileName));
        	}
        }
    }
}
