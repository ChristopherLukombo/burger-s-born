package fr.esgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@ElementCollection(targetClass = String.class)
	@Column(name = "ingredients")
	private List<String> ingredients;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "available")
	private boolean available;
	
	@ManyToOne
	private Category category;
	
	@ManyToMany(mappedBy = "products")
	private List<Menu> menus;
	
	@ManyToOne
	private Manager manager;

	@ManyToMany(mappedBy = "products")
	@JsonIgnore
	private List<Command> commands;

	public Product() {
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

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
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
		Product product = (Product) o;
		return Double.compare(product.price, price) == 0 &&
				available == product.available &&
				Objects.equals(id, product.id) &&
				Objects.equals(name, product.name) &&
				Objects.equals(ingredients, product.ingredients) &&
				Objects.equals(category, product.category) &&
				Objects.equals(menus, product.menus) &&
				Objects.equals(manager, product.manager) &&
				Objects.equals(commands, product.commands);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, ingredients, price, available, category, menus, manager, commands);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [");
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
		if (ingredients != null) {
			builder.append("ingredients=");
			builder.append(ingredients);
			builder.append(", ");
		}
		builder.append("price=");
		builder.append(price);
		builder.append(", available=");
		builder.append(available);
		builder.append(", ");
		if (category != null) {
			builder.append("category=");
			builder.append(category);
			builder.append(", ");
		}
		if (menus != null) {
			builder.append("menus=");
			builder.append(menus);
			builder.append(", ");
		}
		if (manager != null) {
			builder.append("manager=");
			builder.append(manager);
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
