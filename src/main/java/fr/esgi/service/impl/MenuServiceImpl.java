package fr.esgi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.config.Constants;
import fr.esgi.dao.ManagerRepository;
import fr.esgi.dao.MenuRepository;
import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.mapper.MenuMapper;
import fr.esgi.service.mapper.ProductMapper;

@Transactional
@Service("MenuService")
public class MenuServiceImpl implements MenuService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);


	private final MenuRepository menuRepo;

	private final ManagerRepository managerRepo;

	private final ProductRepository productRepo;

	private final MenuMapper menuMapper;
	
	private final ProductMapper productMapper;
	
    @Autowired
	public MenuServiceImpl(MenuRepository menuRepo, ManagerRepository managerRepo, ProductRepository productRepo,
			MenuMapper menuMapper, ProductMapper productMapper) {
		this.menuRepo = menuRepo;
		this.managerRepo = managerRepo;
		this.productRepo = productRepo;
		this.menuMapper = menuMapper;
		this.productMapper = productMapper;
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
	public Optional<MenuDTO> findOne(Long id) {
		return menuRepo.findById(id)
				.map(menuMapper::menuToMenuDTO);
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
	public Page<MenuDTO> findAll(int page,int size) {
		 LOGGER.debug("Request to get all products");
		
		return menuRepo.findAll(PageRequest.of(page, size))
		           .map(menuMapper::menuToMenuDTO);
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

	@Transactional(readOnly = true)
	@Override
	public List<ProductDTO> findProductsByMenuId(Long id, String categoryName) {
		Optional<List<Product>> map = menuRepo.findById(id)
				.map(Menu::getProducts);
		
		
		List<Product> list;
		if (map.isPresent()) {
			list = map.get();
			if (null == list || list.isEmpty()) {
				return Collections.emptyList();
			}
			return list.stream()
					.filter(p -> categoryName.equalsIgnoreCase(p.getCategory().getName()))
					.map(productMapper::productToProductDTO)
					.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductDTO> findProductsByCategoryName(String categoryName) {
		return productRepo.findAllByCategoryName(categoryName).stream()
				.map(productMapper::productToProductDTO)
				.collect(Collectors.toList());
	}

	 /**
     * Returns the four trends menus.
     * @return the list of entities
     */
	@Transactional(readOnly = true)
	@Override
	public List<MenuDTO> findAllTrendsMenus() {
		List<MenuDTO> menusDTO = menuRepo.findAllTrendsMenus(Constants.PAID, 4).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
		Collections.shuffle(menusDTO);
		return menusDTO;
	}

}
