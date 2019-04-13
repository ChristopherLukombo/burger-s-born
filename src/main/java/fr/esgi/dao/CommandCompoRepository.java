package fr.esgi.dao;

import fr.esgi.domain.CommandCompo;
import fr.esgi.domain.CommandCompoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommandCompoRepository entity.
 * @author christopher
 */
@Repository
public interface CommandCompoRepository extends JpaRepository<CommandCompo, CommandCompoID> {


}
