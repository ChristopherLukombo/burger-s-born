package fr.esgi.domain;

import java.io.Serializable;
import java.util.Objects;

public class CommandCompoID implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Command command;
    private Employee employee;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
        CommandCompoID that = (CommandCompoID) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(command, that.command) &&
                Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, command, employee);
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommandCompoID [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (command != null) {
			builder.append("command=");
			builder.append(command);
			builder.append(", ");
		}
		if (employee != null) {
			builder.append("employee=");
			builder.append(employee);
		}
		builder.append("]");
		return builder.toString();
	}
}
