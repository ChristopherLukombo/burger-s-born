package fr.esgi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="category")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Category  {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<Product>();

}