package fr.esgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(max = 150)
	@Column(length = 150)
	private String name;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Product> products;

	public Category() { }

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category category = (Category) o;
		return Objects.equals(id, category.id) &&
				Objects.equals(name, category.name) &&
				Objects.equals(products, category.products);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, products);
	}

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", name='" + name + '\'' +
				", products=" + products +
				'}';
	}
}
