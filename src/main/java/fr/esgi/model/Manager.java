package fr.esgi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="MANAGER")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Manager {

	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String pseudo;
	private String password;
	private LocalDate createDate;
	private String email;
	private LocalDate birthDay;
	
//	private Product hisProduct;
	
//	private Menu hisMenu;


}