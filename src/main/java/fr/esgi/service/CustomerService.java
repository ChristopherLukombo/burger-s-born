package fr.esgi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CustomerDTO;

@Service
public interface CustomerService {

	Optional<CustomerDTO> findOne(Long id);
	
	Long findByUserName(String userName);
}