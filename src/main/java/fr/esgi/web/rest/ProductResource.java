package fr.esgi.web.rest;

import static fr.esgi.config.Utils.getLang;

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

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * REST controller for managing products.
 *
 * @author mickael
 */
@Api(value = "Product")
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
	 * GET  /products : get all the products.
	 *
	 * @param page
	 * @param size
	 * @return all products
	 * @throws BurgerSTerminalException
	 */
	@ApiOperation(value = "Get all the products.")
	@GetMapping("/products")
	public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
		LOGGER.debug("REST request to find all products");
		final Page<ProductDTO> products = productService.findAll(page, size);
		if (null == products || products.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), 
					messageSource.getMessage(ErrorMessage.ERROR_PRODUCTS_NOT_FOUND, null, getLang("fr")));
		}

		return ResponseEntity.ok(products);
	}

	/**
	 * GET  /products/category : get all the products by category name.
	 * 
	 * @param page
	 * @param size
	 * @param categorieName
	 * @return
	 * @throws BurgerSTerminalException
	 */
	@ApiOperation(value = "Get all the products by category name.")
	@GetMapping("/products/category")
	public ResponseEntity<Page<ProductDTO>> getProductsByCategoryName(
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("categorieName") String categorieName) throws BurgerSTerminalException {
		Page<ProductDTO> products = productService.findProductsByCategoryName(PageRequest.of(page, size), categorieName);
		if (products.isEmpty()) {
			throw new BurgerSTerminalException(
					HttpStatus.NOT_FOUND.value(), 
					messageSource.getMessage(ErrorMessage.ERROR_PRODUCTS_NOT_FOUND, null, getLang("fr")));
		}
		return ResponseEntity.ok(products);
	}

	/**
	 * POST  /new/product : create a product.
	 * 
	 * @param productDTO
	 * @return
	 * @throws BurgerSTerminalException
	 * @throws URISyntaxException
	 */
	@ApiOperation(value = "Create a product.")
	@PostMapping("/new/product")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) throws BurgerSTerminalException, URISyntaxException {
		LOGGER.debug("REST request to create a product: {}", productDTO);
		if (null != productDTO.getId()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
					messageSource.getMessage(ErrorMessage.ERROR_NEW_PRODUCT_ID_EXIST, null, getLang("fr")));
		}
		ProductDTO result = productService.save(productDTO);
		return ResponseEntity.created(new URI("/api/product" + result.getId()))
				.body(result);
	}

	/**
	 * DELETE  /delete/product/{id} : delete a product.
	 * 
	 * @param id the id of the productDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@ApiOperation(value = "Delete a product.")
	@DeleteMapping("/delete/product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		LOGGER.debug("REST request to delete a product: {}", id);
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * PUT  /product : update a product.
	 * 
	 * @param productDTO
	 * @return the ResponseEntity with status 200 (OK) and with body the assignmentModuleDTO
	 * @throws BurgerSTerminalException if the id of command is empty.
	 */
	@ApiOperation(value = "Update a product.")
	@PutMapping("/product")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO) throws BurgerSTerminalException {
		LOGGER.debug("REST request to update a product: {}", productDTO);
		if (null == productDTO.getId()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
					messageSource.getMessage(ErrorMessage.ERROR_PRODUCT_MUST_HAVE_ID, null, getLang("fr")));
		}
		ProductDTO result = productService.update(productDTO);
		return ResponseEntity.ok()
				.body(result);
	}

}