package fr.esgi.web.rest;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<Menu> createMenu(@QueryParam("id") Long id ,@RequestBody @Valid Menu menu){
    	List<Product> p = new ArrayList<Product>();
    	return new ResponseEntity<Menu>(menuService.createMenu(menu, id, p),HttpStatus.OK);
    }
    
    @GetMapping("/menus")
    public ResponseEntity<List<Menu>> findAll(HttpServletRequest request) {
        LOGGER.debug("REST request to find all menus");
        return new ResponseEntity<List<Menu>>(menuService.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/find/menu/id")
    public ResponseEntity<Menu> findById(@QueryParam("id") Long id ) {
        return new ResponseEntity<Menu>(menuService.findById(id), HttpStatus.FOUND);
    }
    
    @GetMapping("/find/menu/name")
    public ResponseEntity<Menu> findByName(@QueryParam("name") String name ) {
        return new ResponseEntity<Menu>(menuService.findByName(name), HttpStatus.FOUND);
    }
    
    @PutMapping("/menu/update")
    public ResponseEntity<Menu> findById(@RequestBody @Valid Menu menu) {
        return new ResponseEntity<Menu>(menuService.update(menu), HttpStatus.OK);
    }
    
 
    @DeleteMapping("/delete/by/name")
    public ResponseEntity deleteByName(@QueryParam("name") String name) {
    	menuService.deleteByName(name);
        return new ResponseEntity(HttpStatus.OK);
    } 
    
    @DeleteMapping("/delete/by/id")
    public ResponseEntity deleteById(@QueryParam("id") Long id) {
    	menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    } 
}
