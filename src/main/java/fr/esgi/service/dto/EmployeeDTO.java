package fr.esgi.service.dto;

import fr.esgi.domain.Employee;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO representing a employee.
 */
public class EmployeeDTO {

	private Long id;

	private LocalDate hiringDate;

    private Long userId;

    public EmployeeDTO() {
        // Empty constructor needed for Jackson.
    }

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.hiringDate = employee.getHiringDate();
        this.userId = employee.getUser().getId();
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
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(hiringDate, that.hiringDate) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hiringDate, userId);
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmployeeDTO [");
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
		if (userId != null) {
			builder.append("userId=");
			builder.append(userId);
		}
		builder.append("]");
		return builder.toString();
	}
}
