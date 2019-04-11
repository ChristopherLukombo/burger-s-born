package fr.esgi.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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
	
	@Column(name="address")
	private String address;
	
	@Column(name="zipCode")
	private Long zipCode;
	
	@Column(name="city")
	private String city;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="pseudo")
	private String pseudo;
	
	@Column(name="createDate")
	private LocalDate createDate;
	
	@Column(name="birthDay")
	private LocalDate birthDay;
	
	@OneToOne
    @JoinColumn
    @MapsId
	private Command command;



}