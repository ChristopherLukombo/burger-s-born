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
import org.springframework.data.domain.Pageable;
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

	private final MenuRepository menuRepository;

	private final ManagerRepository managerRepository;

	private final ProductRepository productRepository;

	private final MenuMapper menuMapper;

	private final ProductMapper productMapper;

	@Autowired
	public MenuServiceImpl(MenuRepository menuRepo, ManagerRepository managerRepo, ProductRepository productRepo,
			MenuMapper menuMapper, ProductMapper productMapper) {
		this.menuRepository = menuRepo;
		this.managerRepository = managerRepo;
		this.productRepository = productRepo;
		this.menuMapper = menuMapper;
		this.productMapper = productMapper;
	}

	@Override
	public MenuDTO createMenu(MenuDTO menuDTO, Long idManager, String...productNames ) {
		final Optional<Manager> manager = managerRepository.findById(idManager);

		if (manager.isPresent()) {
			menuDTO.setManagerId(manager.get().getId());
		}

		List<Product> products = this.findProduct(productNames);
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu.setProducts(products);
		menu = menuRepository.save(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	@Override
	public Optional<MenuDTO> findOne(Long id) {
		return menuRepository.findById(id)
				.map(menuMapper::menuToMenuDTO);
	}

	@Override
	public MenuDTO findByName(String name) {
		Menu menu = menuRepository.findByName(name);
		return menuMapper.menuToMenuDTO(menu);
	}

	@Override
	public MenuDTO update(MenuDTO menuDTO) {
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu = menuRepository.saveAndFlush(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<MenuDTO> findAll(int page,int size) {
		LOGGER.debug("Request to get all products");

		return menuRepository.findAll(PageRequest.of(page, size))
				.map(menuMapper::menuToMenuDTO);
	}

	@Override
	public void delete(Long id) {
		// TODO : refactorer la méthode.
		try {
			menuRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByName(String name) {
		menuRepository.deleteByName(name);
	}

	@Override
	public void addProducts(String menuName,String...productNames) {
		List<Product> products = this.findProduct(productNames);
		final Optional<Menu> menu = Optional.ofNullable(menuRepository.findByName(menuName));
		if (menu.isPresent()) {
			menu.get().setProducts(products);
		}
		menuRepository.saveAndFlush(menu.get());
	}

	private List<Product> findProduct(String...strings){
		List<Product> products = new ArrayList<Product>();
		for(String nameProduct : strings) {

			final Optional<Product> product = productRepository.findOneByNameIgnoreCase(nameProduct);

			if(product.isPresent()) {
				products.add(product.get());
			}
		}
		return products;
	}

	@Override
	public void removeProduct(String menuName,String...productNames) {
		Menu menu = menuRepository.findByName(menuName);
        
		// TODO : refactorer la méthode.
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
		Optional<List<Product>> map = menuRepository.findById(id)
				.map(Menu::getProducts);

		// TODO : refactorer la méthode.
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
	public Page<ProductDTO> findProductsByCategoryName(Pageable pageable, String categoryName) {
		LOGGER.debug("Request to find all products by categoryName: {}", categoryName);
		return productRepository.findAllByCategoryName(pageable, categoryName)
				.map(productMapper::productToProductDTO);
	}

	/**
	 * Returns the four trends menus.
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	@Override
	public List<MenuDTO> findAllTrendsMenus() {
		List<MenuDTO> menusDTO = menuRepository.findAllTrendsMenus(Constants.PAID, 4).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
		Collections.shuffle(menusDTO);
		return menusDTO;
	}

	/**
	 * SaveAll menus.
	 * 
	 * @param menusDTO the list of entities to save
	 * @return the list of entities
	 */
	@Override
	public List<MenuDTO> saveAll(List<MenuDTO> menusDTO) {
		LOGGER.debug("Request to save all menus");
		List<Menu> menus = menusDTO.stream()
				.map(menuMapper::menuDTOToMenu)
				.collect(Collectors.toList());

		return menuRepository.saveAll(menus).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}
}
