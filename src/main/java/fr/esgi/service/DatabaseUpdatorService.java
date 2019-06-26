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
	 * Import products file in database.
	 * 
	 * @param fileToImport
	 * @param fileFormat
	 * @return
	 * @throws BurgerSTerminalException
	 */
	List<ProductDTO> importProductsFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException;

	/**
	 * Import Menus file in database.
	 * 
	 * @param inputFile
	 * @param fileFormat
	 * @return
	 * @throws BurgerSTerminalException
	 */
	List<MenuDTO> importMenusFile(MultipartFile inputFile, String fileFormat) throws BurgerSTerminalException;
}
