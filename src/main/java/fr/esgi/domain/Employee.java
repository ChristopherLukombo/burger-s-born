package fr.esgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee {

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

	public Employee() { }

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
}
