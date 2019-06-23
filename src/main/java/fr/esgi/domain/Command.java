package fr.esgi.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	private ZonedDateTime date;
	
	@ManyToMany(targetEntity = Product.class)
	private List<Product> products;

	@OneToOne	
	private Customer customer;
	
    @OneToMany(mappedBy = "command")
	private List<CommandCompo> commandCompos;

	@ManyToMany
	private List<Menu> menus;
	
	private String paymentId;
	
	private BigDecimal price;
	
	private String saleId;

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

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
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

	public BigDecimal getPrice() {
		return price;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandCompos, customer, date, id, menus, orderStatus, paymentId, price, products, saleId);
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
				&& Objects.equals(paymentId, other.paymentId) && Objects.equals(price, other.price)
				&& Objects.equals(products, other.products) && Objects.equals(saleId, other.saleId);
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
			builder.append(", ");
		}
		if (price != null) {
			builder.append("price=");
			builder.append(price);
			builder.append(", ");
		}
		if (saleId != null) {
			builder.append("saleId=");
			builder.append(saleId);
		}
		builder.append("]");
		return builder.toString();
	}
}
