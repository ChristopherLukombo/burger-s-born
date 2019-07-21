package fr.esgi.service;

import org.springframework.stereotype.Service;

/**
 * Service Interface for managing Manager.
 */
@Service
public interface ManagerService {

	/**
	 * Get the manager by userName.
	 * 
	 * @param userName
	 * @return the id of the manager
	 */
	Long findByUserName(String userName);
}
