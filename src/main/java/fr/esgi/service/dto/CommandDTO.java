package fr.esgi.service.dto;


import fr.esgi.domain.Command;

import java.time.LocalDate;

public class CommandDTO {

	private Long id;

	private String orderStatus;

	private LocalDate date;
	
	private Long customerId;

	public CommandDTO() {
		// Empty constructor needed for Jackson.
	}

	public CommandDTO(Command command) {
		this.id = command.getId();
		this.orderStatus = command.getOrderStatus();
		this.date = command.getDate();
		this.customerId = command.getCustomer().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}
