package fr.esgi.service.dto;

import fr.esgi.domain.Employee;

import java.time.LocalDate;

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
}
