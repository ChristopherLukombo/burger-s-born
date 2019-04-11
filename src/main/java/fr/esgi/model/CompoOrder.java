package fr.esgi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="compoOrder")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class CompoOrder {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="price")
	private double price;
	
	@Column(name="state")
	private String state;
	
	@Column(name="quantity")
	private int quantity;
	
	@OneToOne
	@JoinColumn
	private Employee employee;
	
	@OneToOne
	@JoinColumn
	private Order order;
}