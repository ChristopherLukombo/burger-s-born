package fr.esgi.service.dto;

import fr.esgi.domain.Command;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;

import java.util.List;
import java.util.Objects;

import javax.persistence.ManyToMany;

public class MenuDTO {

	private Long id;

	private String name;
	
	private double price;
	
	private Boolean available;
	
	private Long managerId;
	
	private List<Product> products;

	
	public MenuDTO() {
		// Empty constructor needed for Jackson.
	}

	public MenuDTO(Menu menu) {
		this.id = menu.getId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.available = menu.getAvailable();
		this.managerId = menu.getManager().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	

	@Override
	public String toString() {
		return "MenuDTO [id=" + id + ", name=" + name + ", price=" + price + ", available=" + available + ", managerId="
				+ managerId + "]";
	}


	
}
