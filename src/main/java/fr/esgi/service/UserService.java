package fr.esgi.service;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * Upload file in folder
     * @param file
     * @param userId
     * @throws BurgerSTerminalException
     */
    void store(MultipartFile file, Long userId) throws BurgerSTerminalException;
}
