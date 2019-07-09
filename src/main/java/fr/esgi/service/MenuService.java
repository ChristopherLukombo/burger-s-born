package fr.esgi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

/**
 * Service Interface for managing Menu.
 */
@Service
public interface MenuService {

	/**
	 * Get all the menus.
	 * 
	 * @return the list of entities 
	 */
	Page<MenuDTO> findAll(int page,int size);

	/**
	 * Get all the products by menu id.
	 * 	
	 * @return the list of entities
	 */
	List<ProductDTO> findProductsByMenuId(Long id, String categoryName);

	/**
	 * Returns the four trends menus.
	 * 
	 * @return the list of entities
	 */
	List<MenuDTO> findAllTrendsMenus();

	/**
	 * Save all the menus.
	 * 
	 * @param menusDTO the list of entities to save
	 * @return the list of entities
	 */
	List<MenuDTO> saveAll(List<MenuDTO> menusDTO);
}
