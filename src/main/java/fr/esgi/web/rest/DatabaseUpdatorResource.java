package fr.esgi.web.rest;

import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing the import
 */
@Api(value = "DatabaseUpdator")
@RestController
@RequestMapping("/api")
public class DatabaseUpdatorResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUpdatorResource.class);
	
	private final DatabaseUpdatorService databaseUpdatorService;
	
	private final MessageSource messageSource;

    @Autowired
	public DatabaseUpdatorResource(DatabaseUpdatorService databaseUpdatorService, MessageSource messageSource) {
		this.databaseUpdatorService = databaseUpdatorService;
		this.messageSource = messageSource;
	}

	/**
     * POST : /product/import : Add or Update products from a JSON/CSV file
     *
     * @param inputfile file used to import product
     * @return Message on the operation
     * @throws BurgerSTerminalException
     */
    @ApiOperation(value = "Add or Update products from a JSON/CSV file.")
    @PostMapping(value = "/product/import", headers = "content-type=multipart/*")
    public ResponseEntity<List<ProductDTO>> uploadProductsFile(
    		@RequestPart("importfile") MultipartFile inputfile, @RequestParam(required = true) String fileFormat, Locale locale) throws BurgerSTerminalException {
    	List<ProductDTO> importFile;
		if (!fileFormat.equalsIgnoreCase(FilenameUtils.getExtension(inputfile.getOriginalFilename()))) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorMessage.THE_FILE_FORMAT_IS_INVALID);
		}
    	LOGGER.debug("Request to import file : {}", inputfile.getName());
    	try {
    		importFile = databaseUpdatorService.importProductsFile(inputfile, fileFormat);
    	} catch (DataIntegrityViolationException e) {
    		throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				messageSource.getMessage(ErrorMessage.ERROR_FAIL_BATCH_IMPORT_PRODUCT, null, locale));
    	} catch (BurgerSTerminalException e) {
    		throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				messageSource.getMessage(ErrorMessage.ERROR_BATCH_INVALID, null, locale));
    	}
    	
    	return ResponseEntity.ok().body(importFile);
    }
    
    /**
     * POST : /menu/import : Add or Update products from a JSON/CSV file
     *
     * @param inputFile file used to import product
     * @return Message on the operation
     * @throws BurgerSTerminalException
     */
    @ApiOperation(value = "Add or Update products from a JSON/CSV file.")
    @PostMapping(value = "/menu/import", headers = "content-type=multipart/*")
    public ResponseEntity<List<MenuDTO>> uploadMenuFile(
    		@RequestPart("importfile") MultipartFile inputFile, @RequestParam(required = true) String fileFormat, Locale locale) throws BurgerSTerminalException {
    	List<MenuDTO> importFile;
		if (!fileFormat.equalsIgnoreCase(FilenameUtils.getExtension(inputFile.getOriginalFilename()))) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorMessage.THE_FILE_FORMAT_IS_INVALID);
		}
    	LOGGER.debug("Request to import file : {}", inputFile.getName());
    	try {
    		importFile = databaseUpdatorService.importMenusFile(inputFile, fileFormat);
    	} catch (DataIntegrityViolationException e) {
    		throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				messageSource.getMessage(ErrorMessage.ERROR_FAIL_BATCH_IMPORT_PRODUCT, null, locale));
    	} catch (BurgerSTerminalException e) {
    		throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				messageSource.getMessage(ErrorMessage.ERROR_BATCH_INVALID, null, locale));
    	}

    	return ResponseEntity.ok().body(importFile);
    }
}
