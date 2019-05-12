package fr.esgi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esgi.dao.ManagerRepository;
import fr.esgi.dao.MenuRepository;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepo;

	@Autowired
	private ManagerRepository managerRepo;

	@Override
	public Menu createMenu(Menu menu, Long idManager, List<Product> products) {
		Manager manager = managerRepo.getOne(idManager);
		menu.setManager(manager);
		menu.setProducts(products);
		return menuRepo.save(menu);
	}


	@Override
	public Menu findById(Long id) {
		return menuRepo.getOne(id);
	}

	@Override
	public Menu findByName(String name) {
		return menuRepo.findByName(name);
	}

	@Override
	public Menu update(Menu menu) {
		return menuRepo.saveAndFlush(menu);
	}

	@Override
	public void delete(Long id) {
		menuRepo.deleteById(id);		
	}

	@Override
	public void deleteByName(String name) {
			menuRepo.deleteByName(name);
	}

	@Override
	public List<Menu> getAll() {
		return menuRepo.findAll();
	}

}
