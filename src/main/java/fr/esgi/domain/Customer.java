package fr.esgi.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@OneToMany(fetch = FetchType.LAZY)
	private List<Command> commands;

	@ManyToOne
	private User user;

	public Customer() {
		// Empty constructor needed for Hibernate.
	}

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

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, city, commands, id, user, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(city, other.city)
				&& Objects.equals(commands, other.commands) && Objects.equals(id, other.id)
				&& Objects.equals(user, other.user) && zipCode == other.zipCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (address != null) {
			builder.append("address=");
			builder.append(address);
			builder.append(", ");
		}
		builder.append("zipCode=");
		builder.append(zipCode);
		builder.append(", ");
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (commands != null) {
			builder.append("commands=");
			builder.append(commands);
			builder.append(", ");
		}
		if (user != null) {
			builder.append("user=");
			builder.append(user);
		}
		builder.append("]");
		return builder.toString();
	}
}
