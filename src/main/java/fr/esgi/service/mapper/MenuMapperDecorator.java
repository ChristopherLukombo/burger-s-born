package fr.esgi.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.esgi.domain.Category;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

public class MenuMapperDecorator implements MenuMapper {

	@Override
	public MenuDTO menuToMenuDTO(Menu menu) {
		if (menu == null) {
			return null;
		}

		MenuDTO menuDTO = new MenuDTO();

		menuDTO.setName(menu.getName());
		menuDTO.setAvailable(menu.getAvailable());
		menuDTO.setId(menu.getId());
		Long id = menuManagerId(menu);
		if (id != null) {
			menuDTO.setManagerId(id);
		}
		menuDTO.setPrice(menu.getPrice());
		menuDTO.setProductsDTO(getProducts(menu));
		return menuDTO;
	}

	private List<ProductDTO> getProducts(Menu menu) {
		if (null == menu.getProducts()) {
			return Collections.emptyList();
		}
		List<ProductDTO> productsDTO = new ArrayList<>();
		for (Product product : menu.getProducts()) {
			ProductDTO p = new ProductDTO();
			p.setId(product.getId());
			p.setName(product.getName());
			p.setPrice(product.getPrice());
			p.setCategoryId(getCategory(product));
			p.setAvailable(product.isAvailable());
			p.setManagerId(getManagerId(product));
			productsDTO.add(p);
		}
		return productsDTO;
	}

	private Long getCategory(Product product) {
		Category category = product.getCategory();
		if (category == null) {
			return null;
		}
		Long id = category.getId();
		if (id == null) {
			return null;
		}
		return id;
	}

	private Long getManagerId(Product product) {
		Manager manager = product.getManager();
		if (manager == null) {
			return null;
		}
		Long id = manager.getId();
		if (id == null) {
			return null;
		}
		return id;
	}

	@Override
	public Menu menuDTOToMenu(MenuDTO menuDTO) {
		if (menuDTO == null) {
			return null;
		}

		Menu menu = new Menu();

		menu.setManager(menuDTOToManager(menuDTO));
		menu.setPrice(menuDTO.getPrice());
		menu.setName(menuDTO.getName());
		menu.setAvailable(menuDTO.getAvailable());
		menu.setId(menuDTO.getId());
		menu.setProducts(getProducts(menuDTO));

		return menu;
	}

	private List<Product> getProducts(MenuDTO menuDTO) {
		if (menuDTO.getProductsDTO() == null) {
			return Collections.emptyList();
		}
		List<Product> products = new ArrayList<>();
		for (ProductDTO productDTO : menuDTO.getProductsDTO()) {
			Product p = new Product();
			p.setId(productDTO.getId());
			p.setName(productDTO.getName());
			p.setPrice(productDTO.getPrice());
			p.setAvailable(productDTO.isAvailable());
			p.setCategory(new Category());
			products.add(p);
		}
		return products;
	}

	private Long menuManagerId(Menu menu) {
		if (menu == null) {
			return null;
		}
		Manager manager = menu.getManager();
		if (manager == null) {
			return null;
		}
		Long id = manager.getId();
		if (id == null) {
			return null;
		}
		return id;
	}

	protected Manager menuDTOToManager(MenuDTO menuDTO) {
		if (menuDTO == null) {
			return null;
		}

		Manager manager = new Manager();

		manager.setId(menuDTO.getManagerId());

		return manager;
	}
}
