package fr.esgi.web.rest;

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static fr.esgi.config.Utils.getLang;


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

    private final DatabaseUpdatorService databaseUpdatorService;

    private final MessageSource messageSource;


    @Autowired
    public ProductResource(ProductService productService, DatabaseUpdatorService databaseUpdatorService, MessageSource messageSource) {
        this.productService = productService;
        this.databaseUpdatorService = databaseUpdatorService;
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
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO productDTO, @RequestParam("id") Long id, @RequestParam("available") Boolean available, @RequestParam("name") String name, @RequestParam("price") double price) throws BurgerSTerminalException {
        LOGGER.debug("REST request to add new product");
        return new ResponseEntity<ProductDTO>(productService.addProduct(productDTO, id, available, name, price), HttpStatus.OK);
    }

    /**
     * POST : /product/import/json : Add or Update products from a JSON file
     *
     * @param inputFile file used to import product
     * @return Message on the operation
     * @throws BurgerSTerminalException
     */
    @PostMapping(value = "/product/import/json", headers = "content-type=multipart/*")
    public ResponseEntity<String> upload(
            @RequestPart("importfile") MultipartFile inputFile) throws BurgerSTerminalException {

        JSONArray objects = databaseUpdatorService.importFile(inputFile, "json");
        List<ProductDTO> productDTOS = productService.convertJsonToArray(objects);

        try {
            productService.addAll(productDTOS);
        } catch (DataIntegrityViolationException e) {
            throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    messageSource.getMessage(ErrorMessage.ERROR_FAIL_BATCH_IMPORT_PRODUCT, null, getLang("fr")) +
                            " " + inputFile.getOriginalFilename(), e);
        }

        return ResponseEntity.ok()
                .body("Successfully uploaded : " + inputFile.getOriginalFilename());
    }


}