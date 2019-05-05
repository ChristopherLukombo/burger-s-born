package fr.esgi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(CommandCompoID.class)
public class CommandCompo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column
	private double price;

	@NotNull
	@Column
	private String state;

	@NotNull
	@Column
	private double quantity;
	
    @Id
    @ManyToOne
	private Command command;
    
    @Id
    @ManyToOne
    private Employee employee;

	public CommandCompo() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommandCompo that = (CommandCompo) o;
		return Double.compare(that.price, price) == 0 &&
				Double.compare(that.quantity, quantity) == 0 &&
				Objects.equals(id, that.id) &&
				Objects.equals(state, that.state) &&
				Objects.equals(command, that.command) &&
				Objects.equals(employee, that.employee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, price, state, quantity, command, employee);
	}

	@Override
	public String toString() {
		return "CommandCompo{" +
				"id=" + id +
				", price=" + price +
				", state='" + state + '\'' +
				", quantity=" + quantity +
				", command=" + command +
				", employee=" + employee +
				'}';
	}
}
