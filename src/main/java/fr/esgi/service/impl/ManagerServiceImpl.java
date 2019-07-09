package fr.esgi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.ManagerRepository;
import fr.esgi.domain.Manager;
import fr.esgi.service.ManagerService;

/**
 * Service Implementation for managing Manager.
 */
@Service("ManagerService")
@Transactional
public class ManagerServiceImpl implements ManagerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerServiceImpl.class);
	
	private final ManagerRepository managerRepository;
	
	@Autowired
	public ManagerServiceImpl(ManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
	}

	/**
     * Get the manager by userName.
     * 
     * @param userName
     * @return the id of customer
     */
    @Override
    @Transactional(readOnly = true)
    public Long findByUserName(String userName) {
    	LOGGER.debug("Request to find Manager : {}", userName);
    	return managerRepository.findByUserName(userName)
    			.map(Manager::getId)
    			.orElse(null);
    }
}
