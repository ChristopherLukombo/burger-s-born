package fr.esgi.web.rest;

import fr.esgi.domain.FileInfo;
import fr.esgi.service.DatabaseUpdatorService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;


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

    @Autowired
    public ProductResource(ProductService productService, DatabaseUpdatorService databaseUpdatorService) {
        this.productService = productService;
        this.databaseUpdatorService = databaseUpdatorService;
    }

    /**
     * GET  /product : find all products.
     *
     * @param request the HTTP request
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

    @PostMapping(value = "/product/import/json", headers = "content-type=multipart/*")
    public ResponseEntity upload(@RequestParam("importfile") MultipartFile inputFile) {

        HttpHeaders headers = new HttpHeaders();
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        JSONArray products = databaseUpdatorService.importFile(inputFile, "json");

        for (int i = 0; i < products.length(); i++) {
            JSONObject obj = (JSONObject) products.get(i);
            ProductDTO product = new ProductDTO();
            product.setId(obj.getLong("id"));
            product.setName(obj.getString("name"));
            product.setPrice(obj.getDouble("price"));
            product.setAvailable(obj.getBoolean("available"));
            product.setCategoryId(obj.getLong("categoryId"));
            product.setManagerId(obj.getLong("managerId"));

            productDTOS.add(product);
        }

        for (ProductDTO p : productDTOS) {
            // HTTP 500 Quand élément déja existant
            productService.addProduct(p);
        }


        headers.add("File Uploaded Succesfully - ", inputFile.getOriginalFilename());
        return new ResponseEntity(headers, HttpStatus.OK);


    }


}