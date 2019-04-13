package fr.esgi.service.dto;

import fr.esgi.domain.Customer;

/**
 * A DTO representing a customer.
 */
public class CustomerDTO {

	private Long id;

	private String address;
	
	private int zipCode;

	private String city;

	private Long commandId;

	private Long userId;

	public CustomerDTO() {
		// Empty constructor needed for Jackson.
	}

	public CustomerDTO(Customer customer) {
		this.id = customer.getId();
		this.address = customer.getAddress();
		this.zipCode = customer.getZipCode();
		this.city = customer.getCity();
		this.commandId = customer.getCommand().getId();
		this.userId = customer.getUser().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
