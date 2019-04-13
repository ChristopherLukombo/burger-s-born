package fr.esgi.dao;

import fr.esgi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerRepository entity.
 * @author christopher
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
