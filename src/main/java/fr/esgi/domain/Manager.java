package fr.esgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Manager {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "manager")
	@JsonIgnore
	private List<Product> products;

	@OneToMany(mappedBy = "manager")
	@JsonIgnore
	private List<Menu> menus;

	@ManyToOne
	private User user;

	public Manager() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Manager manager = (Manager) o;
		return Objects.equals(id, manager.id) &&
				Objects.equals(products, manager.products) &&
				Objects.equals(menus, manager.menus) &&
				Objects.equals(user, manager.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, products, menus, user);
	}

	@Override
	public String toString() {
		return "Manager{" +
				"id=" + id +
				", products=" + products +
				", menus=" + menus +
				", user=" + user +
				'}';
	}
}
