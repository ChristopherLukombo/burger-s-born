package fr.esgi.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="COMMAND")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Command{

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="orderStatus")
	private String orderStatus;

	@Column(name="date")
	private LocalDate date;
	
	@ManyToMany(targetEntity=Product.class)
	private List<Product> products = new ArrayList<Product>();

	@OneToOne(mappedBy = "command")
	private Customer customer;
	
    @OneToMany(mappedBy = "command")
	private List<CommandCompo> commandCompos = new ArrayList<CommandCompo>();


	@ManyToMany(mappedBy = "commands")
	private List<Menu> menus;

}