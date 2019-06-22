package fr.esgi.service.dto;

import fr.esgi.domain.CommandCompo;

import java.io.Serializable;
import java.util.Objects;

public class CommandCompoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String state;
	
	private Long commandId;
    
    private Long employeeId;

	public CommandCompoDTO() {
		// Empty constructor needed for Jackson.
	}

	public CommandCompoDTO(CommandCompo commandCompo) {
		this.id = commandCompo.getId();
		this.state = commandCompo.getState();
		this.commandId = commandCompo.getCommand().getId();
		this.employeeId = commandCompo.getEmployee().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCommandId() {
		return commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandId, employeeId, id, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandCompoDTO other = (CommandCompoDTO) obj;
		return Objects.equals(commandId, other.commandId) && Objects.equals(employeeId, other.employeeId)
				&& Objects.equals(id, other.id) && Objects.equals(state, other.state);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommandCompoDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		if (commandId != null) {
			builder.append("commandId=");
			builder.append(commandId);
			builder.append(", ");
		}
		if (employeeId != null) {
			builder.append("employeeId=");
			builder.append(employeeId);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
