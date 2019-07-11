package fr.esgi.web.rest;

import static fr.esgi.config.Utils.getLang;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing products.
 *
 */
@Api(value = "DatabaseUpdator")
@RestController
@RequestMapping("/api")
public class MenuResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuResource.class);

	private final MenuService menuService;

	private final MessageSource messageSource;

	@Autowired
	public MenuResource(MenuService menuService, MessageSource messageSource) {
		this.menuService = menuService;
		this.messageSource = messageSource;
	}

	/**
	 * GET  /menu/all : get all the menus.
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws BurgerSTerminalException
	 */
	@ApiOperation(value = "Get all the menus.")
	@GetMapping("/menu/all")
	public ResponseEntity<Page<MenuDTO>> findAll(@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
		LOGGER.debug("REST request to find all menus");
		final Page<MenuDTO> menus = menuService.findAll(page, size);
		if (null == menus || menus.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(),
					messageSource.getMessage(ErrorMessage.ERROR_MENUS_NOT_FOUND, null, getLang("fr")));
		}
		return ResponseEntity.ok(menus);
	}

	/**
	 * GET  "/menus/products : get the products by menu id.
	 * 
	 * @param id
	 * @param categorieName
	 * @return
	 * @throws BurgerSTerminalException 
	 */
	@ApiOperation(value = "Get the products by menu id.")
	@GetMapping("/menus/products")
	public ResponseEntity<List<ProductDTO>> findProductsByMenuId(@RequestParam Long id, @RequestParam String categorieName) throws BurgerSTerminalException {
		final List<ProductDTO> productsDTO = menuService.findProductsByMenuId(id, categorieName);
		if (null == productsDTO || productsDTO.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PRODUCTS_NOT_FOUND, null, getLang("fr")));
		}
		return ResponseEntity.ok(productsDTO);
	}

	/**
	 * GET  /menus/trends : get the four trends menus.
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws BurgerSTerminalException if there is no menus.
	 */
	@ApiOperation(value = "Get the four trends menus.")
	@GetMapping("/menus/trends")
	public ResponseEntity<List<MenuDTO>> getAllTrendsMenus() throws BurgerSTerminalException {
		LOGGER.debug("REST request to get all trends menus");
		final List<MenuDTO> menusDTO = menuService.findAllTrendsMenus();
		if (menusDTO.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), 
					messageSource.getMessage(ErrorMessage.ERROR_MENUS_NOT_FOUND, null, getLang("fr")));
		}
		return ResponseEntity.ok(menusDTO);
	}
}
