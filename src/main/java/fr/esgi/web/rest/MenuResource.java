package fr.esgi.web.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

import org.springframework.web.bind.annotation.RestController;

import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.MenuService;
import fr.esgi.service.ProductService;

@RestController
@RequestMapping("/api")
public class MenuResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuResource.class);

	private final MenuService menuService;

	@Autowired
	public MenuResource(MenuService menuService) {
		super();
		this.menuService = menuService;
	}

	@PostMapping("/new/menu")
	public ResponseEntity<MenuDTO> createMenu(@RequestParam("id") Long id,@RequestBody @Valid MenuDTO menuDTO,@RequestParam("name") String...productName){
		return new ResponseEntity<MenuDTO>(menuService.createMenu(menuDTO, id, productName),HttpStatus.OK);
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

	//    @GetMapping("/find/menu/id")
	//    public ResponseEntity<MenuDTO> findById(@RequestParam Long id) throws BurgerSTerminalException {
	//    	try {
	//            return ResponseEntity.ok(menuService.findById(id));
	//        } catch (Exception e) { // TODO Mettre le message dans les fichiers messages
	//            throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "ID non trouvé : " + id , e);
	//        }
	//    }

	@GetMapping("/find/menu/name")
	public ResponseEntity<MenuDTO> findByName(@RequestParam String name) {
		return ResponseEntity.ok(menuService.findByName(name));
	}

	@PutMapping("/menu/update")
	public ResponseEntity<MenuDTO> findById(@RequestBody @Valid MenuDTO menuDTO) throws BurgerSTerminalException {
		if (null == menuDTO.getId()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(), "Id of the menu is cannot be null");
		}
		return ResponseEntity.ok(menuService.update(menuDTO));
	}


	@DeleteMapping("/delete/by/name")
	public ResponseEntity<Void> deleteByName(@RequestParam String name) {
		menuService.deleteByName(name);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete/by/id")
	public ResponseEntity<Void> deleteById(@RequestParam Long id) {
		menuService.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/addProduct")
	public ResponseEntity<MenuDTO> addProduct(@RequestParam("menuName") String menuName,@RequestParam("nameProduct") String...nameProducts ){

		if(menuName == null || menuName.isEmpty() || nameProducts == null) {
			return ResponseEntity.notFound().build();
		}

		menuService.addProducts(menuName, nameProducts);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/removeProduct")
	public ResponseEntity<MenuDTO> removeProduct(@RequestParam("menuName") String menuName,@RequestParam("nameProduct") String...nameProducts ){

		if(menuName == null || menuName.isEmpty() || nameProducts == null) {
			return ResponseEntity.notFound().build();
		}

		menuService.removeProduct(menuName, nameProducts);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/menus/{id}")
	public ResponseEntity<MenuDTO> getMenu(@PathVariable Long id) throws BurgerSTerminalException {
		Optional<MenuDTO> menu = menuService.findOne(id);
		if (menu.isPresent()) {
			return ResponseEntity.ok(menu.get());
		} else {
			throw new BurgerSTerminalException(
					HttpStatus.NOT_FOUND.value(), "Menu non trouvé");
		}
	}
	
	@GetMapping("/menus/products")
	public ResponseEntity<List<ProductDTO>> findProductsByMenuId(@RequestParam Long id, @RequestParam  String categorieName) {
		List<ProductDTO> menus = menuService.findProductsByMenuId(id, categorieName);
		return ResponseEntity.ok(menus);
	}
	
	@GetMapping("/products/category")
	public ResponseEntity<List<ProductDTO>> findProductsByCategoryName(@RequestParam  String categorieName) {
		List<ProductDTO> menus = menuService.findProductsByCategoryName(categorieName);
		return ResponseEntity.ok(menus);
	}
	
}
