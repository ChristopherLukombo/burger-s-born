package fr.esgi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Autowired
	public ProductResource(ProductService productService, MessageSource messageSource) {
		this.productService = productService;
	}

	/**
	 * GET  /product : find all products.
	 *
	 * @param page
	 * @param size
	 * @return all products
	 * @throws BurgerSTerminalException
	 */
	@GetMapping("/products")
	public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
		LOGGER.debug("REST request to find all products");
		final Page<ProductDTO> products = productService.findAll(page, size);
		if (null == products || products.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Produits non trouvé");
		}

		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/category")
	public ResponseEntity<Page<ProductDTO>> getProductsByCategoryName(
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
	 * POST  /new/product : save a product.
	 *
	 * @param productDTO
	 * @param id
	 * @param available
	 * @param name
	 * @param price
	 * @return created product
	 * @throws BurgerSTerminalException
	 * @throws URISyntaxException 
	 */
	@PostMapping("new/product")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) throws BurgerSTerminalException, URISyntaxException {
		LOGGER.debug("REST request to create a product: {}", productDTO);
		if (null != productDTO.getId()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
					"Un produit ne peut pas déjà avoir un ID.");
		}
		ProductDTO result = productService.save(productDTO);
		return ResponseEntity.created(new URI("/api/product" + result.getId()))
				.body(result);
	}

	/**
	 * DELETE  /product/{id} : delete a product.
	 * @param id the id of the productDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("delete/product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		LOGGER.debug("REST request to delete a product: {}", id);
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}


	/**
	 * PUT  /product : update a product.
	 * @param productDTO
	 * @return the ResponseEntity with status 200 (OK) and with body the assignmentModuleDTO
	 * @throws BurgerSTerminalException if the id of command is empty.
	 */
	@PutMapping("/product")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO) throws BurgerSTerminalException {
		LOGGER.debug("REST request to update a product: {}", productDTO);
		if (null == productDTO.getId()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
					"Un produit doit avoir un ID.");
		}
		ProductDTO result = productService.update(productDTO);
		return ResponseEntity.ok()
				.body(result);
	}

}