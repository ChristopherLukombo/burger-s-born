package fr.esgi.dao;

import fr.esgi.domain.Command;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommandRepository entity.
 * @author christopher
 */
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

	Optional<Command> findByPaymentId(String paymentId);

	@Query("SELECT c FROM Command c "
			+ "WHERE c.customer.id = :customerId "
			+ "AND orderStatus = :orderStatus ORDER BY date DESC")
	Page<Command> findAllByCustomerId(Pageable pageable,
			@Param("customerId") Long customerId,
			@Param("orderStatus") String orderStatus);

}
