package fr.esgi.web.rest;

import fr.esgi.domain.Product;
import fr.esgi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
     */
    @GetMapping("/product")
    public ResponseEntity<List<Product>> findAll(HttpServletRequest request) {
        LOGGER.debug("REST request to find all products");
        return new ResponseEntity<List<Product>>(productService.findAll(), HttpStatus.OK);
    }

}
