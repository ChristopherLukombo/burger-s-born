package fr.esgi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

@Service
public interface MenuService {
	
	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	Page<MenuDTO> findAll(int page,int size);
	
	/**
	 * 
	 * @param id
	 * @param categoryName
	 * @return
	 */
    List<ProductDTO> findProductsByMenuId(Long id, String categoryName);
    
    /**
     * Returns the four trends menus.
     * @return the list of entities
     */
	List<MenuDTO> findAllTrendsMenus();
	
	/**
	 * SaveAll menus.
	 * 
	 * @param menusDTO the list of entities to save
	 * @return the list of entities
	 */
	List<MenuDTO> saveAll(List<MenuDTO> menusDTO);
}
