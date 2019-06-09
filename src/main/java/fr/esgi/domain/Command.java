package fr.esgi.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Command {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(max = 200)
	@Column(name = "orderStatus")
	private String orderStatus;

	@NotNull
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToMany(targetEntity = Product.class)
	private List<Product> products;

	@OneToOne(mappedBy = "command")
	private Customer customer;
	
    @OneToMany(mappedBy = "command")
	private List<CommandCompo> commandCompos;

	@ManyToMany(mappedBy = "commands")
	private List<Menu> menus;

	public Command() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CommandCompo> getCommandCompos() {
		return commandCompos;
	}

	public void setCommandCompos(List<CommandCompo> commandCompos) {
		this.commandCompos = commandCompos;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Command command = (Command) o;
		return Objects.equals(id, command.id) &&
				Objects.equals(orderStatus, command.orderStatus) &&
				Objects.equals(date, command.date) &&
				Objects.equals(products, command.products) &&
				Objects.equals(customer, command.customer) &&
				Objects.equals(commandCompos, command.commandCompos) &&
				Objects.equals(menus, command.menus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, orderStatus, date, products, customer, commandCompos, menus);
	}

	@Override
	public String toString() {
		return "Command{" +
				"id=" + id +
				", orderStatus='" + orderStatus + '\'' +
				", date=" + date +
				", products=" + products +
				", customer=" + customer +
				", commandCompos=" + commandCompos +
				", menus=" + menus +
				'}';
	}
}
