package fr.esgi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 150)
	@Column(name = "name", length = 150)
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "available")
	private Boolean available;
	
	@ManyToOne
	private Manager manager;
	
	@ManyToMany
	private List<Product> products;
	
	@ManyToMany
	private List<Command> commands;

	public Menu() {
		// Empty constructor needed for Hibernate.
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

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Menu menu = (Menu) o;
		return Double.compare(menu.price, price) == 0 &&
				Objects.equals(id, menu.id) &&
				Objects.equals(name, menu.name) &&
				Objects.equals(available, menu.available) &&
				Objects.equals(manager, menu.manager) &&
				Objects.equals(products, menu.products) &&
				Objects.equals(commands, menu.commands);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, available, manager, products, commands);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu [");
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
		if (manager != null) {
			builder.append("manager=");
			builder.append(manager);
			builder.append(", ");
		}
		if (products != null) {
			builder.append("products=");
			builder.append(products);
			builder.append(", ");
		}
		if (commands != null) {
			builder.append("commands=");
			builder.append(commands);
		}
		builder.append("]");
		return builder.toString();
	}
}
