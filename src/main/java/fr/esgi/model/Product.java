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


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="PRODUCT")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	@Column(name="name")
	private String name;
	
	@Column(name="ingredients")
	private ArrayList<String> ingredients ;
	
	@Column(name="price")
	private double price;
	
	@Column(name="isAvailable")
	private boolean isAvailable;
	
	@ManyToOne
	@JoinColumn
	private Category category;
	
	@ManyToMany(mappedBy = "products")
	private List<Menu> menus = new ArrayList<Menu>();
	
	@ManyToOne
	@JoinColumn
	private Manager manager;
	
	@ManyToMany(mappedBy = "products")
	private List<Order> orders = new ArrayList<Order>();
	
}