package fr.esgi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(value = "MenuResource")
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
	 * POST /menu/create : Create a menu
	 * @throws BurgerSTerminalException 
	 * @throws NoSuchMessageException 
	 * @throws URISyntaxException 
	 * 
	 */
	@ApiOperation(value = "Create a menu")
	@PostMapping("/new/menu")
	public ResponseEntity<MenuDTO> createMenu(@RequestBody @Valid MenuDTO menuDTO, Locale locale) throws BurgerSTerminalException, URISyntaxException{
		LOGGER.debug("REST request to create a menu: {}", menuDTO);
		if (null != menuDTO.getId()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(), messageSource.getMessage("Erreur nouveau menu", null, locale));
		}
		MenuDTO result = menuService.save(menuDTO);
		return ResponseEntity.created(new URI("/api/menu" + result.getId()))
				.body(result);
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
	public ResponseEntity<Page<MenuDTO>> findAll(@RequestParam int page, @RequestParam("size") int size, Locale locale) throws BurgerSTerminalException {
		LOGGER.debug("REST request to find all menus");
		final Page<MenuDTO> menus = menuService.findAll(page, size);
		if (null == menus || menus.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(),
					messageSource.getMessage(ErrorMessage.ERROR_MENUS_NOT_FOUND, null, locale));
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
	public ResponseEntity<List<ProductDTO>> findProductsByMenuId(@RequestParam Long id, @RequestParam String categorieName, Locale locale) throws BurgerSTerminalException {
		final List<ProductDTO> productsDTO = menuService.findProductsByMenuId(id, categorieName);
		if (null == productsDTO || productsDTO.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PRODUCTS_NOT_FOUND, null, locale));
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
	public ResponseEntity<List<MenuDTO>> getAllTrendsMenus(Locale locale) throws BurgerSTerminalException {
		LOGGER.debug("REST request to get all trends menus");
		final List<MenuDTO> menusDTO = menuService.findAllTrendsMenus();
		if (menusDTO.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), 
					messageSource.getMessage(ErrorMessage.ERROR_MENUS_NOT_FOUND, null, locale));
		}
		return ResponseEntity.ok(menusDTO);
	}
	
	
	/**
	 * 
	 * DELETE /delete/menu/{id}
	 * @param id the id of the menutDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	
	@ApiOperation(value = "Delete a menu.")
	@DeleteMapping("/delete/menu/{id}")
	public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
		LOGGER.debug("REST request to delete a menu: {}", id);
		menuService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	/**
	 * PUT  /menu : update a menu.
	 * 
	 * @param menutDTO
	 * @return the ResponseEntity with status 200 (OK) and with body the assignmentModuleDTO
	 * @throws BurgerSTerminalException if the id of command is empty.
	 */
	@ApiOperation(value = "Update a menu.")
	@PutMapping("/menu")
	public ResponseEntity<MenuDTO> updateMenu(@RequestBody @Valid MenuDTO menuDTO, Locale locale) throws BurgerSTerminalException {
		LOGGER.debug("REST request to update a menu: {}", menuDTO);
		if(menuDTO.getId() == null) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PRODUCT_MUST_HAVE_ID, null, locale));
		}
		MenuDTO result = menuService.update(menuDTO);
		return ResponseEntity.ok()
				.body(result);
	}
}
