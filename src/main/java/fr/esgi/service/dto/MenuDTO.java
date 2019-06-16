package fr.esgi.service.dto;

import fr.esgi.domain.Menu;

import java.util.List;
import java.util.Objects;

public class MenuDTO {

	private Long id;

	private String name;
	
	private double price;
	
	private Boolean available;
	
	private Long managerId;
	
	private List<ProductDTO> productsDTO;

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

	public List<ProductDTO> getProductsDTO() {
		return productsDTO;
	}

	public void setProductsDTO(List<ProductDTO> productsDTO) {
		this.productsDTO = productsDTO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(available, id, managerId, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuDTO other = (MenuDTO) obj;
		return Objects.equals(available, other.available) && Objects.equals(id, other.id)
				&& Objects.equals(managerId, other.managerId) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		builder.append("price=");
		builder.append(price);
		builder.append(", ");
		if (available != null) {
			builder.append("available=");
			builder.append(available);
			builder.append(", ");
		}
		if (managerId != null) {
			builder.append("managerId=");
			builder.append(managerId);
		}
		builder.append("]");
		return builder.toString();
	}
}
