package fr.esgi.model;

import java.io.Serializable;
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
	private float price;
	
	@Column(name="isAvailable")
	private Boolean isAvailable;
	
	@ManyToOne
	@JoinColumn
	private Manager manager;
	
	@ManyToMany
	private List<Product> products = new ArrayList<Product>();
	
	@ManyToMany
	private List<Command> commands = new ArrayList<Command>();

}