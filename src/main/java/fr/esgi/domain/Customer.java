package fr.esgi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "address")
	private String address;
	
	@Column(name = "zipCode")
	private int zipCode;

	@Size(max = 100)
	@Column(name = "city", length = 150)
	private String city;

	@ManyToOne
	private Command command;

	@ManyToOne
	private User user;

	public Customer() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return zipCode == customer.zipCode &&
				Objects.equals(id, customer.id) &&
				Objects.equals(address, customer.address) &&
				Objects.equals(city, customer.city) &&
				Objects.equals(command, customer.command) &&
				Objects.equals(user, customer.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, address, zipCode, city, command, user);
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", address='" + address + '\'' +
				", zipCode=" + zipCode +
				", city='" + city + '\'' +
				", command=" + command +
				", user=" + user +
				'}';
	}
}
