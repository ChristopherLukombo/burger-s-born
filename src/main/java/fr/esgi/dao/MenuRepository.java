package fr.esgi.dao;

import fr.esgi.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuRepository entity.
 * @author christopher
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {


}
