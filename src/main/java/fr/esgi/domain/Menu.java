package fr.esgi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

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

	public Menu() { }

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
}
