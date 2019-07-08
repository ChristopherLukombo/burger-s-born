package fr.esgi.web.rest;

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

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.CategoryService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.CategoryDTO;
import fr.esgi.service.dto.ProductDTO;

/**
 * REST controller for managing category.
 *
 * @author mickael
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    private final MessageSource messageSource;

    @Autowired
    public CategoryResource(CategoryService categoryService, MessageSource messageSource) {
		this.categoryService = categoryService;
		this.messageSource = messageSource;
	}



	/**
     * GET  /category : find all categories.
     *
     * @return all categories
     * @throws BurgerSTerminalException
     */
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws BurgerSTerminalException {
        LOGGER.debug("REST request to find all categories");
        final List<CategoryDTO> categories = categoryService.findAll();
        if (null == categories || categories.isEmpty()) {
            throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Catégorie non trouvé");
        }
        return ResponseEntity.ok(categories);
    }
}