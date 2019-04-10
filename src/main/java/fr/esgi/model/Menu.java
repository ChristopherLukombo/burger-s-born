package fr.esgi.model;

import java.util.ArrayList;
import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private String name;
	private double price;
	private Boolean isAvailable;
	
//	@ManyToOne
//	private Manager hisManager;
	
//	@ManyToMany(targetEntity=Product.class)
//	private Product hisProducts;
	
//	@ManyToMany(targetEntity=Order.class)
//	private Order hisOrders;

}