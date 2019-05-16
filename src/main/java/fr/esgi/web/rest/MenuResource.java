package fr.esgi.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.esgi.domain.Product;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;

@RestController
@RequestMapping("/api/menu")
public class MenuResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuResource.class);

    private final MenuService menuService;

    @Autowired
	public MenuResource(MenuService menuService) {
		this.menuService = menuService;
	}
    
    @PostMapping("/new/menu")
    public ResponseEntity<MenuDTO> createMenu(@RequestParam("id") Long id,@RequestBody @Valid MenuDTO menuDTO,@RequestParam("name") String...productName){
    	return new ResponseEntity<MenuDTO>(menuService.createMenu(menuDTO, id, productName),HttpStatus.OK);
    }
    
    @GetMapping("/menu/all")
    public ResponseEntity<List<MenuDTO>> findAll() {
    	LOGGER.debug("REST request to find all menus");
        return ResponseEntity.ok(menuService.getAll());
    }
    
    @GetMapping("/find/menu/id")
    public ResponseEntity<MenuDTO> findById(@RequestParam Long id) throws BurgerSTerminalException {
    	try {
            return ResponseEntity.ok(menuService.findById(id));
        } catch (Exception e) { // TODO Mettre le message dans les fichiers messages
            throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "ID non trouvé : " + id , e);
        }
    }
    
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
}
