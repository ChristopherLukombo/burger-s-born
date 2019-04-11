package fr.esgi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ORDER")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Order {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="date")
	private LocalDate date;

	@Column(name="orderStatus")
	private String orderStatus;

	@ManyToMany(targetEntity=Product.class)
	private List<Product> products = new ArrayList<Product>();

	@OneToOne
	@JoinColumn
	//@MapsId
	private Customer customer;

	@OneToOne(mappedBy = "order")
	private CompoOrder compoOrder;


	@ManyToMany(targetEntity=Menu.class)
	private List<Menu> menus;


}