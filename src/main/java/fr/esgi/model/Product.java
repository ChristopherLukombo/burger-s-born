package fr.esgi.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;


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
	private String name;
	private ArrayList<String> ingredient;
	private double price;
	private boolean isAvailable;
	
	
//	@ManyToMany(targetEntity=Menu.class)
//	private Menu hisMenus;
	
//	@ManyToOne
//	private Category category;
	
//	@ManyToOne
//	private Manager hisManager;
	
//	@ManyToMany(targetEntity=Order.class)
//	private Order hisOrders;
	
}