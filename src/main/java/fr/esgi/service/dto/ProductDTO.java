package fr.esgi.service.dto;

import fr.esgi.domain.Product;

public class ProductDTO {

	private Long id;

	private String name;

	private double price;
	
	private boolean isAvailable;
	
	private Long categoryId;
	
	private Long managerId;

	public ProductDTO() {
		// Empty constructor needed for Jackson.
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.isAvailable = product.isAvailable();
		this.categoryId = product.getCategory().getId();
		this.managerId = product.getManager().getId();
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

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
}
