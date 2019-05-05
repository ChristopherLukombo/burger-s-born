package fr.esgi.service.dto;

import fr.esgi.domain.Menu;

import java.util.Objects;

public class MenuDTO {

	private Long id;

	private String name;
	
	private double price;
	
	private Boolean available;
	
	private Long managerId;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MenuDTO menuDTO = (MenuDTO) o;
		return Double.compare(menuDTO.price, price) == 0 &&
				Objects.equals(id, menuDTO.id) &&
				Objects.equals(name, menuDTO.name) &&
				Objects.equals(available, menuDTO.available) &&
				Objects.equals(managerId, menuDTO.managerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, available, managerId);
	}

	@Override
	public String toString() {
		return "MenuDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price=" + price +
				", available=" + available +
				", managerId=" + managerId +
				'}';
	}
}
