package fr.esgi.dao;

import fr.esgi.domain.Menu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MenuRepository entity.
 * @author christopher
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	Menu findByName(String name);
	void deleteByName(String name);
	
	@Query(value = "SELECT * FROM command_menus "
			+ "INNER JOIN menu ON command_menus.menus_id = menu.id "
			+ "INNER JOIN command ON command_menus.command_id = command.id "
			+ "WHERE command.order_status = :orderStatus "
			+ "GROUP BY command_menus.menus_id LIMIT :menusCount", nativeQuery = true)
	List<Menu> findAllTrendsMenus(@Param("orderStatus") String orderStatus, @Param("menusCount") int menusCount);

}
