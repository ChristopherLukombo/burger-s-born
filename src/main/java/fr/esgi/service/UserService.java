package fr.esgi.service;

import fr.esgi.service.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Service Interface for managing User.
 */
@Service
public interface UserService {

    /**
     * Save the user in database.
     * @param userDTO
     * @param password
     * @return User
     */
    UserDTO registerUser(UserDTO userDTO, String password);

    /**
     * Returns true if the login is already used.
     * @param userDTO
     * @return boolean
     */
    boolean loginIsPresent(UserDTO userDTO);

    /**
     * Returns true if the email is already used.
     * @param userDTO
     * @return boolean
     */
    boolean emailIsPresent(UserDTO userDTO);

}
