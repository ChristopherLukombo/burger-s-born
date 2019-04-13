package fr.esgi.dao;

import fr.esgi.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeRepository entity.
 * @author christopher
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}
