package fr.esgi.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.config.ConfigurationService;
import fr.esgi.config.Constants;
import fr.esgi.config.ErrorMessage;
import fr.esgi.dao.RoleRepository;
import fr.esgi.dao.UserRepository;
import fr.esgi.domain.Role;
import fr.esgi.domain.User;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.UserService;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.service.mapper.UserMapper;

/**
 * Service Implementation for managing User.
 */
@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;
    
    private final ConfigurationService configurationService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
			RoleRepository roleRepository, UserMapper userMapper, ConfigurationService configurationService) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.configurationService = configurationService;
	}

	/**
     * Save the user in database.
     * @param userDTO
     * @param password
     * @return UserDTO
     */
    public UserDTO registerUser(UserDTO userDTO, String password) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setPseudo(userDTO.getPseudo());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setBirthDay(userDTO.getBirthDay());
        newUser.setCreateDate(userDTO.getCreateDate());
        final Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
        if (role.isPresent()) {
            newUser.setRole(role.get());
        }
        newUser.setActivated(true);
        newUser = userRepository.save(newUser);

        LOGGER.debug("Created Information for User: {}", newUser);
        return userMapper.userToUserDTO(newUser);
    }

    /**
     * Returns user by login
     * @param userDTO
     * @return Optional<User>
     */
    public Optional<User> findUserByPseudo(UserDTO userDTO) {
        return userRepository.findOneByPseudoIgnoreCase(userDTO.getPseudo());
    }

    /**
     * Returns user found by email.
     * @param userDTO
     * @return Optional<User>
     */
    public Optional<User> findUserByEmail(UserDTO userDTO) {
        return userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
    }

    /**
     * Upload file in folder
     * @param file
     * @param userId
     * @throws BurgerSTerminalException
     */
    @Override
    public void store(MultipartFile file, Long userId) throws BurgerSTerminalException {
        final Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            try {
                User user = userOptional.get();
                user.setImageUrl(file.getOriginalFilename());
                userRepository.saveAndFlush(user);
                createFolder(userId);
                final Path rootLocation = Paths.get(configurationService.getImagesDirectory() + Constants.IMAGES  + Constants.DELIMITER + userId);
                sendFileToFolder(file, rootLocation);
            } catch (IOException e) {
                throw new BurgerSTerminalException(ErrorMessage.ERROR_DURING_SAVING_FILE, e);
            }
        }
    }
    
    /**
     * Download file from pseudo
     * @param pseudo
     * @return
     * @throws BurgerSTerminalException 
     */
    public Map<String, byte[]> getImageURL(String pseudo) throws BurgerSTerminalException {
    	final Optional<User> user = userRepository.findOneByPseudoIgnoreCase(pseudo);
    	byte[] bytesArray;

    	final Map<String, byte[]> content = new HashMap<>();

    	if (user.isPresent()) {
    		try {
    			final int indexOfBackslash = configurationService.getImagesDirectory().indexOf(Constants.DOUBLE_BACKSLASH);
    			
    			final StringBuilder imageUrl = new StringBuilder();
    			imageUrl.append(configurationService.getImagesDirectory());
    			imageUrl.append(((indexOfBackslash > 0) ?  Constants.IMAGES.replace(Constants.DELIMITER, Constants.DOUBLE_BACKSLASH) : Constants.IMAGES));
    			imageUrl.append((indexOfBackslash > 0) ? Constants.DOUBLE_BACKSLASH : Constants.DELIMITER);
    			imageUrl.append(user.get().getId() + ((indexOfBackslash > 0) ? Constants.DOUBLE_BACKSLASH : Constants.DELIMITER));
    			imageUrl.append(user.get().getImageUrl());
    			final Path path = new File(imageUrl.toString()).toPath();
    			bytesArray = readFile(imageUrl.toString());
    			content.put(Files.probeContentType(path), bytesArray);

    		} catch (IOException e) {
    			throw new BurgerSTerminalException("Le fichier n'existe pas", e);
    		}
    	}
    	return content;
    }

	private byte[] readFile(final String imageUrl) throws IOException {
		return Files.readAllBytes(new File(imageUrl).toPath());
	}

    private void createFolder(Long userId) throws IOException {
        String pathname = configurationService.getImagesDirectory() + Constants.IMAGES;
        File folder = new File(pathname);
        // creation of first folder
        if (!folder.exists()) {
            Files.createDirectories(Paths.get(pathname));
        }
        folder = new File(pathname + Constants.DELIMITER + userId);
        // creation of sub folder
        if (!folder.exists()) {
            Files.createDirectories(Paths.get(pathname + Constants.DELIMITER + userId));
        }
    }

    private void sendFileToFolder(MultipartFile file, Path path) throws IOException {
        Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
    }

}
