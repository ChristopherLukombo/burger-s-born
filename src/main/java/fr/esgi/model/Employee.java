package fr.esgi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="EMPLOYEE")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Employee {
	
	@Id
	@GeneratedValue
	private int id;

	private LocalDate hiringDate;
	private String firstName;
	private String lastName;
	private String pseudo;
	private String password;
	private LocalDate createDate;
	private String email;
	private LocalDate birthDay;

	
}