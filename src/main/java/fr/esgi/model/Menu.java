package fr.esgi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="MENU")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Menu {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private double price;
	
	@Column(name="isAvailable")
	private Boolean isAvailable;
	
	@ManyToOne
	@JoinColumn
	private Manager manager;
	
	@ManyToMany(targetEntity=Product.class)
	private List<Product> products = new ArrayList<Product>();
	
	@ManyToMany(mappedBy = "menus")
	private List<Order> orders = new ArrayList<Order>();

}