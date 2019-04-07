package fr.esgi.service.impl;

import fr.esgi.dao.UserRepository;
import fr.esgi.domain.User;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.UserService;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing User.
 */
@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
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
        newUser.setLogin(userDTO.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        newUser = userRepository.save(newUser);

        LOGGER.debug("Created Information for User: {}", newUser);
        return userMapper.userToUserDTO(newUser);
    }

    /**
     * Returns true if the login is already used.
     * @param userDTO
     * @return boolean
     */
    public boolean loginIsPresent(UserDTO userDTO) {
        return userRepository.findOneByLogin(userDTO.getLogin().toLowerCase())
                .isPresent();
    }

    /**
     * Returns true if the email is already used.
     * @param userDTO
     * @return boolean
     */
    public boolean emailIsPresent(UserDTO userDTO) {
        return userRepository.findOneByEmailIgnoreCase(userDTO.getEmail())
                .isPresent();
    }

}
