package fr.esgi.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="CATEGORY")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Category {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
//	private Product hisProduct;

}