package fr.esgi.service.impl;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.config.Constants;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.MenuService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

@Service("DatabaseUpdatorService")
@Transactional
public class DatabaseUpdatorServiceImpl implements DatabaseUpdatorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUpdatorService.class);

	private final ProductService productService;

    private final MenuService menuService;
    
    @Autowired
	public DatabaseUpdatorServiceImpl(ProductService productService, MenuService menuService) {
		this.productService = productService;
		this.menuService = menuService;
	}

	@Override
	public List<ProductDTO> importProductsFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException {
		if (fileFormat.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.LE_FICHIER_D_IMPORT_EST_VIDE);
		}
		if (!fileFormat.equalsIgnoreCase(FilenameUtils.getExtension(fileToImport.getOriginalFilename()))) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.FORMAT_DE_FICHIER_EST_INVALIDE);
		}
		List<ProductDTO> products = null;
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		try {
			if (Constants.JSON.equals(fileFormat)) {
				products = importJsonProductsFile(fileToImport);
			} else if (Constants.CSV.equals(fileFormat)) {
				products = importCSVProductsFile(fileToImport);
			}
		} catch (IOException e) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.UNE_ERREUR_S_EST_PRODUITE_DURANT, e);
		}
		return products;
	}

	private List<ProductDTO> importJsonProductsFile(MultipartFile fileToImport) throws IOException, BurgerSTerminalException {
		JSONArray jsonArray = getJSONArray(fileToImport);
		JSONObject obj = (JSONObject) jsonArray.get(0);
		if (5 != obj.length()) {
			throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(), "Le fichier ne contient pas le bon nombre de colonne");
		}
		List<ProductDTO> products = convertJsonToList(jsonArray);
		return productService.saveAll(products);
	}

	private List<ProductDTO> convertJsonToList(JSONArray objects) {
		List<ProductDTO> products = new ArrayList<>();

		for (int i = 0; i < objects.length(); i++) {
			JSONObject obj = (JSONObject) objects.get(i);
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(obj.getString(Constants.NAME));
			productDTO.setPrice(obj.getDouble(Constants.PRICE));
			productDTO.setAvailable(obj.getBoolean(Constants.AVAILABLE));
			productDTO.setCategoryId(obj.getLong(Constants.CATEGORY_ID));
			productDTO.setManagerId(obj.getLong(Constants.MANAGER_ID));

			products.add(productDTO);
		}

		return products;
	}

	private List<ProductDTO> importCSVProductsFile(MultipartFile fileToImport) throws IOException, BurgerSTerminalException {
		String csv = convertCSVToString(fileToImport);
		List<ProductDTO> products = convertProductsCSVToList(csv);
		return productService.saveAll(products);
	}

	private List<ProductDTO> convertProductsCSVToList(String csv) throws IOException, BurgerSTerminalException {
		InputStream inputStream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8.name()));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		List<ProductDTO> products = new ArrayList<>();

		String line = null;
		int index = 0;

		while ((line = bufferedReader.readLine()) != null) {
			String[] firstLine = line.split(Constants.COMMA);
			if (5 != firstLine.length && 0 == index) {
				throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(), 
						Constants.LE_FICHIER_NE_CONTIENT_PAS_LE_BON_NOMBRE_DE_COLONNES);
			}
			String[] theLine = line.split(Constants.COMMA);

			if (index > 0) {
				ProductDTO productDTO = new ProductDTO();
				productDTO.setName(theLine[0]);
				productDTO.setPrice(Double.parseDouble(theLine[1]));
				productDTO.setAvailable(Boolean.parseBoolean(theLine[2]));
				productDTO.setCategoryId(Long.parseLong(theLine[3]));
				productDTO.setManagerId(Long.parseLong(theLine[4]));

				products.add(productDTO);
			}
			index++;
		}
		return products;
	}
	
	private static String convertCSVToString(MultipartFile fileToImport) throws IOException {
		InputStream inputStream = fileToImport.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
		return writer.toString();
	}
	
	private static JSONArray getJSONArray(MultipartFile fileToImport) throws IOException {
		InputStream inputStream = fileToImport.getInputStream();
		
		String datas = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
		inputStream.close();
		return new JSONArray(datas);
	}
	
	// Menus

	@Override
	public List<MenuDTO> importMenusFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException {
		if (fileFormat.isEmpty()) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					Constants.LE_FICHIER_D_IMPORT_EST_VIDE);
		}
		if (!fileFormat.equalsIgnoreCase(FilenameUtils.getExtension(fileToImport.getOriginalFilename()))) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
					Constants.FORMAT_DE_FICHIER_EST_INVALIDE);
		}
		List<MenuDTO> menus = null;
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		try {
			if (Constants.JSON.equals(fileFormat)) {
				menus = importJsonMenusFile(fileToImport);
			} else if (Constants.CSV.equals(fileFormat)) {
				menus = importCSVMenusFile(fileToImport);
			}
		} catch (IOException e) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
					Constants.UNE_ERREUR_S_EST_PRODUITE_DURANT, e);
		}
		return menus;
	}

	private List<MenuDTO> importCSVMenusFile(MultipartFile fileToImport) throws IOException, BurgerSTerminalException {
		String csv = convertCSVToString(fileToImport);
		List<MenuDTO> menus = convertMenusCSVToList(csv);
		return menuService.saveAll(menus);
	}
	
	private List<MenuDTO> importJsonMenusFile(MultipartFile fileToImport) throws IOException, BurgerSTerminalException {
		JSONArray jsonArray = getJSONArray(fileToImport);
		JSONObject obj = (JSONObject) jsonArray.get(0);
		if (4 != obj.length()) {
			throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Le fichier ne contient pas le bon nombre de colonnes");
		}
		List<MenuDTO> menus = convertJsonMenusToList(jsonArray);
		return menuService.saveAll(menus);
	}
	
	private List<MenuDTO> convertJsonMenusToList(JSONArray objects) {
		List<MenuDTO> menus = new ArrayList<>();

		for (int i = 0; i < objects.length(); i++) {
			JSONObject obj = (JSONObject) objects.get(i);

			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setName(obj.getString(Constants.NAME));
			menuDTO.setPrice(obj.getDouble(Constants.PRICE));
			menuDTO.setAvailable(obj.getBoolean(Constants.AVAILABLE));
			menuDTO.setManagerId(obj.getLong(Constants.MANAGER_ID));

			menus.add(menuDTO);
		}
		return menus;
	}

	private List<MenuDTO> convertMenusCSVToList(String csv) throws IOException, BurgerSTerminalException {
		InputStream inputStream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8.name()));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		List<MenuDTO> menus = new ArrayList<>();

		String line = null;
		int index = 0;

		while ((line = bufferedReader.readLine()) != null) {
			String[] firstLine = line.split(Constants.COMMA);
			if (4 != firstLine.length && 0 == index) {
				throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						Constants.LE_FICHIER_NE_CONTIENT_PAS_LE_BON_NOMBRE_DE_COLONNES);
			}
			String[] theLine = line.split(Constants.COMMA);

			if (index > 0) {
				MenuDTO menuDTO = new MenuDTO();
				menuDTO.setName(theLine[0]);
				menuDTO.setPrice(Double.parseDouble(theLine[1]));
				menuDTO.setAvailable(Boolean.parseBoolean(theLine[2]));
				menuDTO.setManagerId(Long.parseLong(theLine[3]));

				menus.add(menuDTO);
			}
			index++;
		}
		return menus;
	}	
}
