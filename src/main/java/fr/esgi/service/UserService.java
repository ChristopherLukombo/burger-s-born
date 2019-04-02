package fr.esgi.service;

import fr.esgi.domain.User;
import fr.esgi.service.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Service Interface for managing User.
 */
@Service
public interface UserService {

    User registerUser(UserDTO userDTO, String password);

}
