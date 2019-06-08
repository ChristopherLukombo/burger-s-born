package fr.esgi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

@Service
public interface MenuService {
	
	MenuDTO createMenu(MenuDTO menu,Long idManager,String...productNames);
	MenuDTO findById(Long id) throws BurgerSTerminalException;
	MenuDTO findByName(String name);
	MenuDTO update(MenuDTO menu);
	List<MenuDTO> getAll();
	
	void addProducts(String menuName,String...productNames);
	void removeProduct(String menuName,String...productNames);

	void delete(Long id);
	void deleteByName(String name);
}
