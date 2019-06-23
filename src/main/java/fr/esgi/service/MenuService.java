package fr.esgi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

@Service
public interface MenuService {
	
	MenuDTO createMenu(MenuDTO menu,Long idManager,String...productNames);
	Optional<MenuDTO> findOne(Long id);
	MenuDTO findByName(String name);
	MenuDTO update(MenuDTO menu);
	Page<MenuDTO> findAll(int page,int size);
	
	void addProducts(String menuName,String...productNames);
	void removeProduct(String menuName,String...productNames);

	void delete(Long id);
	void deleteByName(String name);
	
    List<ProductDTO> findProductsByMenuId(Long id, String categoryName);
    
    Page<ProductDTO> findProductsByCategoryName(Pageable pageable, String categoryName);
    
    /**
     * Returns the four trends menus.
     * @return the list of entities
     */
	List<MenuDTO> findAllTrendsMenus();
}
