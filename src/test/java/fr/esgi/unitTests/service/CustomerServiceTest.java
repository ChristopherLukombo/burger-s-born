package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.esgi.dao.CustomerRepository;
import fr.esgi.domain.Command;
import fr.esgi.domain.Customer;
import fr.esgi.domain.User;
import fr.esgi.service.dto.CustomerDTO;
import fr.esgi.service.impl.CustomerServiceImpl;
import fr.esgi.service.mapper.CustomerMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceTest {

	private static final String PSEUDO = "Ben";

	private static final int ZIP_CODE = 75100;

	private static final String PARIS = "Paris";

	private static final String TEST = "TEST";

	private static final long ID = 1L;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerMapper customerMapper;

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	private static CustomerDTO getCustomerDTO() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(ID);
		customerDTO.setAddress(TEST);
		customerDTO.setZipCode(75100);
		customerDTO.setCity(PARIS);
		customerDTO.setUserId(ID);

		return customerDTO;
	}

	private static Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setAddress(TEST);
		customer.setZipCode(ZIP_CODE);
		customer.setCity(PARIS);
		customer.setUser(getUser());
		customer.setCommands(Arrays.asList(getCommand()));

		return customer;
	}

	private static User getUser() {
		User user = new User();
		user.setId(ID);
		user.setPseudo(PSEUDO);
		return user;
	}

	private static Command getCommand() {
		Command command = new Command();
		command.setId(ID);
		command.setOrderStatus(TEST);
		command.setDate(ZonedDateTime.now());
		command.setPaymentId(TEST);
		command.setPrice(new BigDecimal(1));
		command.setSaleId(TEST);
		return command;
	}

	@Test
	public void shouldFindOneWhenIsOK() {
		// Given
		Customer customer = getCustomer();
		CustomerDTO customerDTO = getCustomerDTO();

		// When
		when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
		when(customerMapper.customerToCustomerDTO((Customer) any())).thenReturn(customerDTO);

		// Then
		assertThat(customerServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(customerDTO));
	}
	
	@Test
	public void shouldFindOneWhenIsKO() {
		// Given
		Customer customer = null;
		CustomerDTO customerDTO = null;

		// When
		when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
		when(customerMapper.customerToCustomerDTO((Customer) any())).thenReturn(customerDTO);

		// Then
		assertThat(customerServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(customerDTO));
	}
	
	@Test
	public void shouldFindByUserNameWhenIsOK() {
		// Given
		Customer customer = getCustomer();
		CustomerDTO customerDTO = getCustomerDTO();

		// When
		when(customerRepository.findByUserName(anyString())).thenReturn(Optional.ofNullable(customer));
		when(customerMapper.customerToCustomerDTO((Customer) any())).thenReturn(customerDTO);

		// Then
		assertThat(customerServiceImpl.findByUserName(PSEUDO)).isEqualTo(customerDTO.getId());
	}
	
	@Test
	public void shouldFindByUserNameWhenIsKO() {
		// Given
		Customer customer = null;
		CustomerDTO customerDTO = null;

		// When
		when(customerRepository.findByUserName(anyString())).thenReturn(Optional.ofNullable(customer));
		when(customerMapper.customerToCustomerDTO((Customer) any())).thenReturn(customerDTO);

		// Then
		assertThat(customerServiceImpl.findByUserName(PSEUDO)).isEqualTo(null);
	}
}
