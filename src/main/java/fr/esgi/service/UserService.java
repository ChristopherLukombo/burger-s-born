package fr.esgi.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.domain.User;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.dto.UserDTO;

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
     * Returns user by login
     * @param userDTO
     * @return Optional<User>
     */
    Optional<User> findUserByPseudo(UserDTO userDTO);

    /**
     * Returns user found by email.
     * @param userDTO
     * @return Optional<User>
     */
    Optional<User> findUserByEmail(UserDTO userDTO);

    /**
     * Upload file in folder
     * @param file
     * @param userId
     * @throws BurgerSTerminalException
     */
    void store(MultipartFile file, Long userId) throws BurgerSTerminalException;
    
    /**
     * Download file from pseudo
     * @param pseudo
     * @return
     * @throws BurgerSTerminalException 
     */
     Map<String, byte[]> getImageURL(String pseudo) throws BurgerSTerminalException;

     /**
      * Returns the current user.
      */
     Optional<UserDTO> findCurrentUser();
     
}
