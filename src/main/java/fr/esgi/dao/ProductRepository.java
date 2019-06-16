package fr.esgi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.esgi.domain.Product;

/**
 * Spring Data JPA repository for the ProductRepository entity.
 * @author christopher
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	 Optional<Product> findOneByNameIgnoreCase(String name);
	 
	 @Query("SELECT p FROM Product p WHERE lower(p.category.name) LIKE concat('%',lower(:categoryName),'%')")
	 List<Product> findAllByCategoryName(@Param("categoryName") String categoryName);
	 
	 @Query(value = "SELECT * "
			 + "FROM menu_products, product "
			 + "WHERE menu_products.products_id = product.id "
			 + "AND menu_products.menus_id = :menuId", nativeQuery = true)
	 List<Product> findAllByMenuId(@Param("menuId") Long menuId);
}
