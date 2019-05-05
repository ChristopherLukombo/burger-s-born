package fr.esgi.domain;

import java.io.Serializable;
import java.util.Objects;

public class CommandCompoID implements Serializable {
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
        return "CommandCompoID{" +
                "id=" + id +
                ", command=" + command +
                ", employee=" + employee +
                '}';
    }
}
