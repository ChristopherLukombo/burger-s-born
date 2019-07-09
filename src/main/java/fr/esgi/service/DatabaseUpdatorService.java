package fr.esgi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

/**
 * Interface for the file import based database update
 * @author Guillaume Tako
 */
@Service
public interface DatabaseUpdatorService {

	/**
	 * Import JSON or CSV file of products. 
	 * 
	 * @return the list of entities
	 */
	List<ProductDTO> importProductsFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException;

	/**
	 * Import JSON or CSV file of menus. 
	 * 
	 * @return the list of entities
	 */
	List<MenuDTO> importMenusFile(MultipartFile inputFile, String fileFormat) throws BurgerSTerminalException;
}
