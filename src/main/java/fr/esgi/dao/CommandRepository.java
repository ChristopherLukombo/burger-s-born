package fr.esgi.dao;

import fr.esgi.domain.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommandRepository entity.
 * @author christopher
 */
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {


}
