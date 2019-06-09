package fr.esgi.service.dto;

import fr.esgi.domain.Product;

import java.util.Objects;

public class ProductDTO {

	private Long id;

	private String name;

	private double price;
	
	private boolean available;
	
	private Long categoryId;
	
	private Long managerId;

	public ProductDTO() {
		// Empty constructor needed for Jackson.
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.available = product.isAvailable();
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
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductDTO that = (ProductDTO) o;
		return Double.compare(that.price, price) == 0 &&
				available == that.available &&
				Objects.equals(id, that.id) &&
				Objects.equals(name, that.name) &&
				Objects.equals(categoryId, that.categoryId) &&
				Objects.equals(managerId, that.managerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, available, categoryId, managerId);
	}

	@Override
	public String toString() {
		return "ProductDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price=" + price +
				", available=" + available +
				", categoryId=" + categoryId +
				", managerId=" + managerId +
				'}';
	}
}
