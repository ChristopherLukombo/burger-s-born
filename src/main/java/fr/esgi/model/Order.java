package fr.esgi.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private LocalDate date;
	private String orderStatus;
	
//	@ManyToMany(targetEntity=Product.class)
//	private Product hisProducts;
	
//	@ManyToMany(targetEntity=Menu.class)
//	private Menu hisMenus;
	
	
//	private ArrayList<OrderLine> orderLine;
	
//	@OneToOne
//	private Customer hisCustomer;
}