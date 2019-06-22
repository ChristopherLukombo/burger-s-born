package fr.esgi.domain;

import java.io.Serializable;
import java.util.Objects;

public class CommandCompoID implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Long id;
    Command command;
    Employee employee;

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
