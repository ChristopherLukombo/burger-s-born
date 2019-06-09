package fr.esgi.dao;

import fr.esgi.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ManagerRepository entity.
 * @author christopher
 */
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {


}
