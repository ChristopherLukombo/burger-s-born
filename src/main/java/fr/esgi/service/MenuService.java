package fr.esgi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;

@Service
public interface MenuService {
	
	Menu createMenu(Menu menu,Long idManager ,List<Product> products);
	Menu findById(Long id);
	Menu findByName(String name);
	Menu update(Menu menu);
	void delete(Long id);
	void deleteByName(String name);
}
