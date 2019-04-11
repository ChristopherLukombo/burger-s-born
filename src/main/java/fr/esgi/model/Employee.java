package fr.esgi.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

	@Column(name="hiringDate")
	private LocalDate hiringDate;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="pseudo")
	private String pseudo;
	
	@Column(name="password")
	private String password;
	
	@Column(name="createDate")
	private LocalDate createDate;
	
	@Column(name="email")
	private String email;
	
	@Column(name="birthDay")
	private LocalDate birthDay;
	
	
	@OneToOne(mappedBy = "employee")
	private CompoOrder compoOrder;

	
}