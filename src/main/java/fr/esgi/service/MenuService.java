package fr.esgi.service;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.dto.MenuDTO;

@Service
public interface MenuService {
	
	MenuDTO createMenu(MenuDTO menu,Long idManager,String...productNames);
	MenuDTO findById(Long id) throws BurgerSTerminalException;
	MenuDTO findByName(String name);
	MenuDTO update(MenuDTO menu);
	Page<MenuDTO> findAll(int page,int size);
	
	void addProducts(String menuName,String...productNames);
	void removeProduct(String menuName,String...productNames);

	void delete(Long id);
	void deleteByName(String name);
}
