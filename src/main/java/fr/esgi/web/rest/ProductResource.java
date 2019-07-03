package fr.esgi.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;


/**
 * REST controller for managing products.
 *
 * @author mickael
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    private final MessageSource messageSource;

    @Autowired
    public ProductResource(ProductService productService, MessageSource messageSource) {
		this.productService = productService;
		this.messageSource = messageSource;
	}



	/**
     * GET  /product : find all products.
     *
     * @param page
     * @param size
     * @return all products
     * @throws BurgerSTerminalException
     */
    @GetMapping("/product")
    public ResponseEntity<Page<ProductDTO>> findProducts(@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
        LOGGER.debug("REST request to find all products");
        final Page<ProductDTO> products = productService.findAll(page, size);
        if (null == products || products.isEmpty()) {
            throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Produits non trouvé");
        }

        return ResponseEntity.ok(products);
    }
    
	@GetMapping("/products/category")
	public ResponseEntity<Page<ProductDTO>> findProductsByCategoryName(
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("categorieName") String categorieName) throws BurgerSTerminalException {
		Page<ProductDTO> products = productService.findProductsByCategoryName(PageRequest.of(page, size), categorieName);
		if (products.isEmpty()) {
			throw new BurgerSTerminalException(
					HttpStatus.NOT_FOUND.value(), "Il n'a pas de produits.");
		}
		return ResponseEntity.ok(products);
	}


    /**
     * POST  /product : add new product.
     *
     * @param productDTO
     * @param id
     * @param available
     * @param name
     * @param price
     * @return created product
     * @throws BurgerSTerminalException
     */
//    @PostMapping("/product")
//    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO productDTO, @RequestParam("id") Long id, @RequestParam("available") Boolean available, @RequestParam("name") String name, @RequestParam("price") double price) throws BurgerSTerminalException {
//        LOGGER.debug("REST request to add new product");
//        return new ResponseEntity<ProductDTO>(productService.addProduct(productDTO, id, available, name, price), HttpStatus.OK);
//    }

}