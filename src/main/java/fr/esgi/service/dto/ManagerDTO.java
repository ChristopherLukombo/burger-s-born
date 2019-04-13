package fr.esgi.service.dto;

import fr.esgi.domain.*;

/**
 * A DTO representing a manager.
 */
public class ManagerDTO {

	private Long id;

	private Long userId;

	public ManagerDTO() {
		// Empty constructor needed for Jackson.
	}

	public ManagerDTO(Manager manager) {
		this.id = manager.getId();
		this.userId = manager.getUser().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
