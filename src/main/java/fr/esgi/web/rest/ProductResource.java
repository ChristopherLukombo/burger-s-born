package fr.esgi.web.rest;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;


/**
 * REST controller for managing products.
 * @author mickael
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);
	 
    private final ProductService productService;
 
    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }
 
    /**
     * GET  /product : find all products.
     *
     * @param request the HTTP request
     * @return all products
     * @throws BurgerSTerminalException
     */
    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> findProducts (@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
        LOGGER.debug("REST request to find all products");
        final Page<ProductDTO> products = productService.findAll(page, size);
        if (null == products || products.isEmpty()) {
            throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Produits non trouvé");
        }
       
        return ResponseEntity.ok(products);
    }
    
    
    /**
     * POST  /product : add new product.
     *
     * @param request the HTTP request
     * @return created product
     * @throws BurgerSTerminalException
     */
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct (@RequestBody @Valid ProductDTO productDTO, @RequestParam("id") Long id, @RequestParam("available") Boolean available, @RequestParam("name") String name, @RequestParam("price") double price) throws BurgerSTerminalException {
        LOGGER.debug("REST request to add new product");        
        return new ResponseEntity<ProductDTO>(productService.addProduct(productDTO, id, available, name, price),HttpStatus.OK);
    }

}