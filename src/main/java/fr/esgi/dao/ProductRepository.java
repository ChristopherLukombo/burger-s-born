package fr.esgi.dao;

import fr.esgi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductRepository entity.
 * @author christopher
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
