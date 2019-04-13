package fr.esgi.service.dto;

import fr.esgi.domain.CommandCompo;

import java.io.Serializable;

public class CommandCompoDTO implements Serializable {
	
	private Long id;

	private double price;

	private String state;

	private double quantity;
	
	private Long commandId;
    
    private Long employeeId;

	public CommandCompoDTO() {
		// Empty constructor needed for Jackson.
	}

	public CommandCompoDTO(CommandCompo commandCompo) {
		this.id = commandCompo.getId();
		this.price = commandCompo.getPrice();
		this.state = commandCompo.getState();
		this.quantity = commandCompo.getQuantity();
		this.commandId = commandCompo.getCommand().getId();
		this.employeeId = commandCompo.getEmployee().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
