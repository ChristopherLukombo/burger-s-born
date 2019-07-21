package fr.esgi.unitTests.web;

import static fr.esgi.unitTests.web.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.esgi.config.ConfigurationService;
import fr.esgi.dao.CustomerRepository;
import fr.esgi.dao.RoleRepository;
import fr.esgi.dao.UserRepository;
import fr.esgi.domain.Customer;
import fr.esgi.domain.Role;
import fr.esgi.domain.User;
import fr.esgi.service.UserService;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.service.impl.UserServiceImpl;
import fr.esgi.service.mapper.UserMapper;
import fr.esgi.web.ManagedUser;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.AccountResource;

@RunWith(MockitoJUnitRunner.Silent.class)
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

	@Mock
	private ConfigurationService configurationService;
	
	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private AccountResource accountResource;

	private User user;

//	private UserDTO userDTO;

	private static final String IMAGES_FOLDER = "./images";

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(accountResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		userService = new UserServiceImpl(passwordEncoder, userRepository, roleRepository, userMapper, configurationService, customerRepository);
		accountResource = new AccountResource(userService, messageSource);
	}

	@Before
	public void createUser() {
		user = getUser();
	}

//	@Before
//	public void createUserDTO() {
//		userDTO = getUserDTO();
//	}

	private static User getUser() {
		User user = new User();
		user.setId(ID);
		user.setPseudo(PSEUDO);
		user.setEmail(EMAIL);
		user.setRole(getRole());
		return user;
	}

	private static Role getRole() {
		Role role = new Role();
		role.setId(ID);
		role.setName("ROLE_ADMIN");
		return role;
	}

//	private static UserDTO getUserDTO() {
//		UserDTO userDTO = new UserDTO();
//		userDTO.setId(ID);
//		userDTO.setPseudo(PSEUDO);
//		userDTO.setEmail(EMAIL);
//		userDTO.setRoleId(getRole().getId());
//		return userDTO;
//	}

	@Test
	public void shouldRegisterWhenIsOK() throws Exception {
		// Given
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID);
		userDTO.setPseudo(PSEUDO);
		userDTO.setEmail(EMAIL);

		ManagedUser managedUser = new ManagedUser();
		managedUser.setPseudo(PSEUDO);
		managedUser.setEmail(EMAIL);
		managedUser.setPassword(PASSWORD);
		Role role = getRole();

		// When
		when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
		when(userRepository.save(mock(User.class))).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isCreated());
	}

	@Test
	public void shouldRegisterWhenIsKO() throws Exception {
		// Given
		UserDTO userDTO = null;

		ManagedUser managedUser = null;

		// When
		when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.save((User) any())).thenReturn(null);
		when(customerRepository.save((Customer) any())).thenReturn(new Customer());
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldRegisterWhenIdNotEmpty() throws Exception {
		// Given
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID);
		userDTO.setPseudo(PSEUDO);
		userDTO.setEmail(EMAIL);

		ManagedUser managedUser = new ManagedUser();
		managedUser.setId(ID);
		managedUser.setPseudo(PSEUDO);
		managedUser.setEmail(EMAIL);
		managedUser.setPassword(PASSWORD);

		// When
		when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.save(mock(User.class))).thenReturn(null);
		when(customerRepository.save((Customer) any())).thenReturn(null);
		when(userMapper.userToUserDTO((User) any())).thenReturn(null);

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldRegisterWhenPasswordNotValid1() throws Exception {
		// Given
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID);
		userDTO.setPseudo(PSEUDO);
		userDTO.setEmail(EMAIL);

		ManagedUser managedUser = new ManagedUser();
		managedUser.setPseudo(PSEUDO);
		managedUser.setEmail(EMAIL);
		managedUser.setPassword("");

		// When
		when(userRepository.save(mock(User.class))).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void shouldRegisterWhenPasswordNotValid2() throws Exception {
		// Given
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID);
		userDTO.setPseudo(PSEUDO);
		userDTO.setEmail(EMAIL);

		ManagedUser managedUser = new ManagedUser();
		managedUser.setPseudo(PSEUDO);
		managedUser.setEmail(EMAIL);

		// When
		when(userRepository.save(mock(User.class))).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldRegisterWhenPseudoAlreadyUsed() throws Exception {
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
		//		when(userService.findUserByPseudo(userDTO)).thenReturn(Optional.ofNullable(user));
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));


		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldRegisterWhenEmailAlreadyUsed() throws Exception {
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
		when(userRepository.findOneByEmailIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(managedUser)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldRegisterWithFileWhenIsOK() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

		// When
		when(configurationService.getImagesDirectory()).thenReturn(".");
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/register/file/1")
				.file(file))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldIsAuthenticatedWhenIsOK() throws Exception {
		// Then
		mockMvc.perform(get("/api/authenticate")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isOk());
	}

	@Test
	public void shouldGetImageURLWhenIsKO1() throws Exception {
		// Given
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID);
		userDTO.setPseudo(PSEUDO);
		userDTO.setEmail(EMAIL);

		ManagedUser managedUser = new ManagedUser();
		managedUser.setPseudo(PSEUDO);
		managedUser.setEmail(EMAIL);
		managedUser.setPassword(PASSWORD);

		final Map<String, byte[]> content = new HashMap<>();
		File file = createFoldersAndFile();
		byte[] readAllBytes = Files.readAllBytes(file.toPath());
		final Path path = new File("filename.txt".toString()).toPath();
		content.put(Files.probeContentType(path), readAllBytes);

		User user = this.user;
		user.setImageUrl("filename.txt");

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
		when(configurationService.getImagesDirectory()).thenReturn(".");

		// Then
		mockMvc.perform(get("/api/users/imageURL/" + PSEUDO)
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isOk());
	}

	private File createFoldersAndFile() throws IOException {
		Files.createDirectory(Paths.get("./images"));
		Files.createDirectory(Paths.get("./images/1"));
		File file = new File("./images/1/filename.txt");
		file.createNewFile();
		return file;
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
