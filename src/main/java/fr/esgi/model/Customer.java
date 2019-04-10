package fr.esgi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="CUSTOMER")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String address;
	private Long zipCode;
	private String city;
	private String firstName;
	private String lastName;
	private String pseudo;
	private LocalDate createDate;
	private LocalDate birthDay;
	
//	@OneToOne
//	private Order hisOrder;



}