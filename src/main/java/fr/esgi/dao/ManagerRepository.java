package fr.esgi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.esgi.domain.Manager;

/**
 * Spring Data JPA repository for the ManagerRepository entity.
 * @author christopher
 */
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

	@Query("SELECT m "
			+ "FROM Manager m "
			+ "WHERE UPPER(m.user.pseudo) = UPPER(:userName) "
			+ "OR UPPER(m.user.email) = UPPER(:userName)")
     Optional<Manager> findByUserName(@Param("userName") String userName);

}
