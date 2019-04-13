package fr.esgi.dao;

import fr.esgi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoryRepository entity.
 * @author christopher
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
