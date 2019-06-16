package fr.esgi.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Command implements Serializable {

	private static final long serialVersionUID = 1L;

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
	
	private String paymentId;

	public Command() {
		// Empty constructor needed for Hibernate.
	}

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

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandCompos, customer, date, id, menus, orderStatus, paymentId, products);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		return Objects.equals(commandCompos, other.commandCompos) && Objects.equals(customer, other.customer)
				&& Objects.equals(date, other.date) && Objects.equals(id, other.id)
				&& Objects.equals(menus, other.menus) && Objects.equals(orderStatus, other.orderStatus)
				&& Objects.equals(paymentId, other.paymentId) && Objects.equals(products, other.products);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Command [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (orderStatus != null) {
			builder.append("orderStatus=");
			builder.append(orderStatus);
			builder.append(", ");
		}
		if (date != null) {
			builder.append("date=");
			builder.append(date);
			builder.append(", ");
		}
		if (products != null) {
			builder.append("products=");
			builder.append(products);
			builder.append(", ");
		}
		if (customer != null) {
			builder.append("customer=");
			builder.append(customer);
			builder.append(", ");
		}
		if (commandCompos != null) {
			builder.append("commandCompos=");
			builder.append(commandCompos);
			builder.append(", ");
		}
		if (menus != null) {
			builder.append("menus=");
			builder.append(menus);
			builder.append(", ");
		}
		if (paymentId != null) {
			builder.append("paymentId=");
			builder.append(paymentId);
		}
		builder.append("]");
		return builder.toString();
	}
}
