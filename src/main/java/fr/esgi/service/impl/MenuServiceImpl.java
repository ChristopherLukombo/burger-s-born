package fr.esgi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.ManagerRepository;
import fr.esgi.dao.MenuRepository;
import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.mapper.MenuMapper;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	private MenuRepository menuRepo;

	private ManagerRepository managerRepo;

	private ProductRepository productRepo;

	private final MenuMapper menuMapper;

	@Autowired
	public MenuServiceImpl(MenuRepository menuRepo, ManagerRepository managerRepo, ProductRepository productRepo,
			MenuMapper menuMapper) {
		this.menuRepo = menuRepo;
		this.managerRepo = managerRepo;
		this.productRepo = productRepo;
		this.menuMapper = menuMapper;
	}

	@Override
	public MenuDTO createMenu(MenuDTO menuDTO, Long idManager, String...productNames ) {
		final Optional<Manager> manager = managerRepo.findById(idManager);

		if (manager.isPresent()) {
			menuDTO.setManagerId(manager.get().getId());
		}

		List<Product> products = this.findProduct(productNames);
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu.setProducts(products);
		menu = menuRepo.save(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	@Override
	public MenuDTO findById(Long id) throws BurgerSTerminalException {
		final Optional<Menu> menu = menuRepo.findById(id);
		if (menu.isPresent()) {
			return menuMapper.menuToMenuDTO(menu.get());
		}
		throw new BurgerSTerminalException("Menu by id " + id + "does not exists");
	}

	@Override
	public MenuDTO findByName(String name) {
		Menu menu = menuRepo.findByName(name);
		return menuMapper.menuToMenuDTO(menu);
	}

	@Override
	public MenuDTO update(MenuDTO menuDTO) {
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
        menu = menuRepo.saveAndFlush(menu);
        return menuMapper.menuToMenuDTO(menu);
	}

	@Override
	@Transactional(readOnly=true)
	public List<MenuDTO> getAll() {
		return menuRepo.findAll().stream()
		           .map(menuMapper::menuToMenuDTO).collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		 try {
	            menuRepo.deleteById(id);
	        } catch (EmptyResultDataAccessException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public void deleteByName(String name) {
        menuRepo.deleteByName(name);
	}




	@Override
	public void addProducts(String menuName,String...productNames) {
		List<Product> products = this.findProduct(productNames);
		final Optional<Menu> menu = Optional.ofNullable(menuRepo.findByName(menuName));
		if (menu.isPresent()) {
			menu.get().setProducts(products);
		}
		 menuRepo.saveAndFlush(menu.get());
	}

	private List<Product> findProduct(String...strings){
		List<Product> products = new ArrayList<Product>();
		for(String nameProduct : strings) {

			final Optional<Product> product = productRepo.findOneByNameIgnoreCase(nameProduct);

			if(product.isPresent()) {
				products.add(product.get());
			}
		}
		return products;
	}

	@Override
	public void removeProduct(String menuName,String...productNames) {
		// TODO Auto-generated method stub
		Menu menu = menuRepo.findByName(menuName);
		
		if(menu != null) {
			List<Product> products = menu.getProducts();
			for(Product p : products) {
				for(String productName : productNames) {
					if(p.getName().equals(productName)) {
						products.remove(p);
					}
				}
			}
		}

	}






}
