package fr.esgi.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.CustomerRepository;
import fr.esgi.domain.Customer;
import fr.esgi.service.CustomerService;
import fr.esgi.service.dto.CustomerDTO;
import fr.esgi.service.mapper.CustomerMapper;

/**
 * Service Implementation for managing Customer.
 */
@Service("CustomerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final CustomerRepository customerRepository;
	
	private final CustomerMapper customerMapper;
   
    @Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	/**
     * Get the customer by id.
     * 
     * @param id the id of customer
     * @return the entity persisted
     */
	@Override
	@Transactional(readOnly = true)
	public Optional<CustomerDTO> findOne(Long id) {
    	LOGGER.debug("Request to find Customer : {}", id);
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO);
	}

    /**
     * Get the customer by userName.
     * 
     * @param userName
     * @return the id of customer
     */
    @Override
    @Transactional(readOnly = true)
    public Long findByUserName(String userName) {
    	LOGGER.debug("Request to find Customer : {}", userName);
    	return customerRepository.findByUserName(userName)
    			.map(Customer::getId)
    			.orElse(null);
    }

}
