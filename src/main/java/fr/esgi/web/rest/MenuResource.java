package fr.esgi.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

@RestController
@RequestMapping("/api")
public class MenuResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuResource.class);

	private final MenuService menuService;

	@Autowired
	public MenuResource(MenuService menuService) {
		this.menuService = menuService;
	}

	@GetMapping("/menu/all")
	public ResponseEntity<Page<MenuDTO>> findAll(@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
		LOGGER.debug("REST request to find all menus");

		final Page<MenuDTO> menus = menuService.findAll(page, size);
		if (null == menus || menus.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Menus non trouvé");
		}

		return ResponseEntity.ok(menus);
	}
	
	@GetMapping("/menus/products")
	public ResponseEntity<List<ProductDTO>> findProductsByMenuId(@RequestParam Long id, @RequestParam  String categorieName) {
		List<ProductDTO> menus = menuService.findProductsByMenuId(id, categorieName);
		return ResponseEntity.ok(menus);
	}
	
	/**
	 * GET  /menus/trends : get the four trends menus.
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws BurgerSTerminalException if there is no menus.
	 */
	@GetMapping("/menus/trends")
	public ResponseEntity<List<MenuDTO>> getAllTrendsMenus() throws BurgerSTerminalException {
		LOGGER.debug("REST request to get all trends menus");
		List<MenuDTO> menusDTO = menuService.findAllTrendsMenus();
		if (menusDTO.isEmpty()) {
			throw new BurgerSTerminalException(
					HttpStatus.NOT_FOUND.value(), "Il n'y a pas de menus.");
		}
		return ResponseEntity.ok(menusDTO);
	}
}
