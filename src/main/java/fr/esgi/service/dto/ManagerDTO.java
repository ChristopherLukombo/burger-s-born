package fr.esgi.service.dto;

import fr.esgi.domain.*;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ManagerDTO that = (ManagerDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId);
	}

	@Override
	public String toString() {
		return "ManagerDTO{" +
				"id=" + id +
				", userId=" + userId +
				'}';
	}
}
