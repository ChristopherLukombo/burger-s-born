package fr.esgi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.esgi.domain.Customer;

/**
 * Spring Data JPA repository for the CustomerRepository entity.
 * @author christopher
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c "
			+ "FROM Customer c "
			+ "WHERE UPPER(c.user.pseudo) = UPPER(:userName) "
			+ "OR UPPER(c.user.email) = UPPER(:userName)")
     Optional<Customer> findByUserName(@Param("userName") String userName);
}
