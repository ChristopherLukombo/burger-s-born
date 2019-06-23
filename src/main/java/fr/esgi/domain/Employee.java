package fr.esgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "hiringDate")
	private LocalDate hiringDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CommandCompo> commandCompo;

    @ManyToOne
    private User user;

	public Employee() {
		// Empty constructor need for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(LocalDate hiringDate) {
		this.hiringDate = hiringDate;
	}

	public List<CommandCompo> getCommandCompo() {
		return commandCompo;
	}

	public void setCommandCompo(List<CommandCompo> commandCompo) {
		this.commandCompo = commandCompo;
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
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) &&
				Objects.equals(hiringDate, employee.hiringDate) &&
				Objects.equals(commandCompo, employee.commandCompo) &&
				Objects.equals(user, employee.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, hiringDate, commandCompo, user);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (hiringDate != null) {
			builder.append("hiringDate=");
			builder.append(hiringDate);
			builder.append(", ");
		}
		if (commandCompo != null) {
			builder.append("commandCompo=");
			builder.append(commandCompo);
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
