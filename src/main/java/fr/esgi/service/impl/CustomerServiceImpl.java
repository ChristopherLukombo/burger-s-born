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
     * Returns customer by id.
     * @param id
     * @return Optional<CustomerDTO>
     */
    @Transactional(readOnly = true)
	@Override
	public Optional<CustomerDTO> findOne(Long id) {
    	LOGGER.debug("Request to find Customer : {}", id);
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO);
	}

    /**
     * Returns customer by userName.
     * @param userName
     * @return Optional<CustomerDTO>
     */
    @Transactional(readOnly = true)
    @Override
    public Long findByUserName(String userName) {
    	return customerRepository.findByUserName(userName)
    			.map(Customer::getId)
    			.orElse(null);
    }

}
