package fr.esgi.web.rest;

import fr.esgi.domain.FileInfo;
import org.apache.commons.io.FilenameUtils;
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
    public ResponseEntity<Page<ProductDTO>> findProducts(@RequestParam int page, @RequestParam("size") int size) throws BurgerSTerminalException {
        LOGGER.debug("REST request to find all products");
        final Page<ProductDTO> products = productService.findAll(page, size);
        if (null == products || products.isEmpty()) {
            throw new BurgerSTerminalException(HttpStatus.NOT_FOUND.value(), "Produits non trouvé");
        }

        return ResponseEntity.ok(products);
    }

    @PostMapping(value = "/product/import/json", headers = "content-type=multipart/*")
    public ResponseEntity<FileInfo> upload(@RequestParam("importfile") MultipartFile inputFile) {

        if ("json".equals(FilenameUtils.getExtension(inputFile.getOriginalFilename()))) {
            FileInfo info = new FileInfo();
            HttpHeaders headers = new HttpHeaders();
            if (!inputFile.isEmpty()) {
                try {
                    String originalFilename = inputFile.getOriginalFilename();
                    File destinationFile = new File("C:\\tmp\\" + originalFilename);
                    inputFile.transferTo(destinationFile);
                    info.setFileName(destinationFile.getPath());
                    info.setFileSize(inputFile.getSize());

                    headers.add("File Uploaded Succesfully - ", originalFilename);
                    return new ResponseEntity<FileInfo>(info, headers, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<FileInfo>(HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }


}