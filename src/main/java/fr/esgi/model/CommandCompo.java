package fr.esgi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="CommandCompo")
public class CommandCompo implements Serializable{
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="price")
	private float  price;
	
	@Column(name="state")
	private String state;
	
	@Column(name="quantity")
	private float quantity;
	
    @Id
    @ManyToOne
    @JoinColumn
	private Command command;
    
    @Id
    @ManyToOne
    @JoinColumn
    private Employee employee;
	
}
