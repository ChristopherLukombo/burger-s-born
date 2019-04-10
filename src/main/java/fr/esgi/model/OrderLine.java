package fr.esgi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ORDER_LINE")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class OrderLine {

	@Id
	@GeneratedValue
	private Long id;
	
	private double price;
	private String state;
	private int quantity;
	
//	@ManyToOne
//	private Employee hisEmployees;
	
//	@ManyToOne
//	private Order hisOrder;
}