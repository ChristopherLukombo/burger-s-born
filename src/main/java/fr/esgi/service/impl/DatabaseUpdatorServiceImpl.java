package fr.esgi.service.impl;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.config.Constants;
import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.MenuService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;

@Service("DatabaseUpdatorService")
@Transactional
public class DatabaseUpdatorServiceImpl implements DatabaseUpdatorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUpdatorServiceImpl.class);

	private final ProductService productService;

	private final MenuService menuService;

	@Autowired
	public DatabaseUpdatorServiceImpl(ProductService productService, MenuService menuService) {
		this.productService = productService;
		this.menuService = menuService;
	}

	@Override
	public List<ProductDTO> importProductsFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException {
		List<ProductDTO> products = null;
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		try {
			if (Constants.JSON.equals(fileFormat)) {
				products = importJsonProductsFile(fileToImport);
			} else if (Constants.CSV.equals(fileFormat)) {
				products = importCSVProductsFile(fileToImport);
			}
		} catch (IOException e) {
			throw new BurgerSTerminalException(ErrorMessage.AN_ERROR_OCCURRED_DURING_THE_IMPORT, e);
		}
		return products;
	}

	private List<ProductDTO> importJsonProductsFile(MultipartFile fileToImport) throws BurgerSTerminalException {
		JSONArray jsonArray = getJSONArray(fileToImport);
		if (!isValidJSONFile(jsonArray, 5)) {
			throw new BurgerSTerminalException(ErrorMessage.THE_FILE_DOES_NOT_CONTAIN_THE_CORRECT_NUMBER_OF_COLUMNS);
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
		if (StringUtils.isEmpty(csv)) {
			return Collections.emptyList();
		}
		List<ProductDTO> products = convertProductsCSVToList(csv);
		return productService.saveAll(products);
	}

	private List<ProductDTO> convertProductsCSVToList(String csv) throws BurgerSTerminalException {
		List<ProductDTO> products = new ArrayList<>();

		try (InputStream inputStream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8.name()))) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line = null;
			int index = 0;

			while ((line = bufferedReader.readLine()) != null) {
				if (!isValidFileCSV(line, 5)) {
					throw new BurgerSTerminalException(ErrorMessage.THE_FILE_DOES_NOT_CONTAIN_THE_CORRECT_NUMBER_OF_COLUMNS);
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
		} catch (Exception e) {
			LOGGER.error(ErrorMessage.ERROR_DURING_READING_OF_FILE, e);
		}
		return products;
	}

	private static String convertCSVToString(MultipartFile fileToImport) throws IOException {
		String csv = null;		
		try (InputStream inputStream = fileToImport.getInputStream()) {
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
			return writer.toString();
		} catch (Exception e) {
			LOGGER.error("Error during reading of the contents of the file: {}", fileToImport.getName());
		}
		return csv;
	}

	private static JSONArray getJSONArray(MultipartFile fileToImport) {
		JSONArray jsonArray = null;
		try (InputStream inputStream = fileToImport.getInputStream()) {
			String datas = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
			jsonArray = new JSONArray(datas);
		} catch (IOException e) {
			LOGGER.error("Error during reading of the contents of the file: {}", fileToImport.getName());
		} 
		return jsonArray;
	}

	private static boolean isValidJSONFile(JSONArray jsonArray, int countColumns) {
		if (jsonArray != null && !jsonArray.isEmpty()) {
			JSONObject obj = (JSONObject) jsonArray.get(0);
			if (countColumns == obj.length()) {
				return true;
			}
		} 
		return false;
	}

	private static boolean isValidFileCSV(String line, int countColumns) {
		String[] firstLine = line.split(Constants.COMMA);
		return countColumns == firstLine.length;
	}	

	// Menus

	@Override
	public List<MenuDTO> importMenusFile(MultipartFile fileToImport, String fileFormat) throws BurgerSTerminalException {
		List<MenuDTO> menus = null;
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		try {
			if (Constants.JSON.equals(fileFormat)) {
				menus = importJsonMenusFile(fileToImport);
			} else if (Constants.CSV.equals(fileFormat)) {
				menus = importCSVMenusFile(fileToImport);
			}
		} catch (IOException e) {
			throw new BurgerSTerminalException(ErrorMessage.AN_ERROR_OCCURRED_DURING_THE_IMPORT, e);
		}
		return menus;
	}

	private List<MenuDTO> importCSVMenusFile(MultipartFile fileToImport) throws IOException, BurgerSTerminalException {
		String csv = convertCSVToString(fileToImport);
		if (StringUtils.isEmpty(csv)) {
			return Collections.emptyList();
		}
		List<MenuDTO> menus = convertMenusCSVToList(csv);
		return menuService.saveAll(menus);
	}

	private List<MenuDTO> importJsonMenusFile(MultipartFile fileToImport) throws BurgerSTerminalException {
		JSONArray jsonArray = getJSONArray(fileToImport);
		if (!isValidJSONFile(jsonArray, 4)) {
			throw new BurgerSTerminalException(ErrorMessage.THE_FILE_DOES_NOT_CONTAIN_THE_CORRECT_NUMBER_OF_COLUMNS);
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
		List<MenuDTO> menus = new ArrayList<>();

		try (InputStream inputStream = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8.name()))) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line = null;
			int index = 0;

			while ((line = bufferedReader.readLine()) != null) {
				if (!isValidFileCSV(line, 4)) {
					throw new BurgerSTerminalException(ErrorMessage.THE_FILE_DOES_NOT_CONTAIN_THE_CORRECT_NUMBER_OF_COLUMNS);
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
		} catch (Exception e) {
			LOGGER.error(ErrorMessage.ERROR_DURING_READING_OF_FILE, e);
		}

		return menus;
	}
}
