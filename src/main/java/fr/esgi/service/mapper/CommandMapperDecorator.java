package fr.esgi.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.esgi.domain.Category;
import fr.esgi.domain.Command;
import fr.esgi.domain.Customer;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

public class CommandMapperDecorator implements CommandMapper {

	@Override
	public CommandDTO commandToCommandDTO(Command command) {
		CommandDTO commandDTO = new CommandDTO();
		commandDTO.setId(command.getId());
		commandDTO.setOrderStatus(command.getOrderStatus());
		commandDTO.setDate(command.getDate());
		commandDTO.setCustomerId(commandCustomerId(command));
		commandDTO.setPaymentId(command.getPaymentId());
		commandDTO.setMenusDTO(getMenusDTO(command));
		commandDTO.setProductsDTO(getProductDTO(command));
		return commandDTO;
	}

	private List<ProductDTO> getProductDTO(Command command) {
		if (null == command) {
			return Collections.emptyList();
		}
		List<ProductDTO> productsDTO = new ArrayList<>();
		for (Product product : command.getProducts()) {
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

	private List<MenuDTO> getMenusDTO(Command command) {
		if (null == command) {
			return Collections.emptyList();
		}
		List<MenuDTO> menusDTO = new ArrayList<>();
		for (Menu menu : command.getMenus()) {
			menusDTO.add(new MenuDTO(menu));
		}
		return menusDTO;
	}

	@Override
	public Command commandDTOToCommand(CommandDTO commandDTO) {
		if (commandDTO == null) {
			return null;
		}
		Command command = new Command();
		command.setId(commandDTO.getId());
		command.setOrderStatus(commandDTO.getOrderStatus());
		command.setDate(commandDTO.getDate());
		Customer customer = getCustomer(commandDTO);
		command.setMenus(getMenus(commandDTO));
		command.setProducts(getProducts(commandDTO));
		command.setPaymentId(commandDTO.getPaymentId());
		command.setCustomer(customer);
		return command;
	}

	private Customer getCustomer(CommandDTO commandDTO) {
		if (commandDTO.getCustomerId() == null) {
			return null;
		}
		Customer customer = new Customer();
		customer.setId(commandDTO.getCustomerId());
		return customer;
	}

	private List<Menu> getMenus(CommandDTO commandDTO) {
		List<MenuDTO> menusDTO = commandDTO.getMenusDTO();
		List<Menu> menus = new ArrayList<>();
		for (MenuDTO menuDTO : menusDTO) {
			Menu m = new Menu();
			m.setId(menuDTO.getId());
			m.setName(menuDTO.getName());
			m.setPrice(menuDTO.getPrice());
			m.setAvailable(menuDTO.getAvailable());
			Long managerId = menuDTO.getManagerId();
			Manager manager = new Manager();
			manager.setId(managerId);
			m.setManager(manager);
			m.setProducts(getProducts(menuDTO));
			menus.add(m);
		}
		return menus;
	}

	private List<Product> getProducts(MenuDTO menuDTO) {
		if (menuDTO.getProductsDTO() == null) {
			return null;
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

	private List<Product> getProducts(CommandDTO commandDTO) {
		List<ProductDTO> productsDTO = commandDTO.getProductsDTO();
		List<Product> products = new ArrayList<>();
		for (ProductDTO productDTO : productsDTO) {
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

	private Long commandCustomerId(Command command) {
		if (command == null) {
			return null;
		}
		Customer customer = command.getCustomer();
		if (customer == null) {
			return null;
		}
		Long id = customer.getId();
		if (id == null) {
			return null;
		}
		return id;
	}
}

