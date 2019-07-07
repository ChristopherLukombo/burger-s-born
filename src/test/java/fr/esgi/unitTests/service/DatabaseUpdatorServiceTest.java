package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.MenuService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.impl.DatabaseUpdatorServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DatabaseUpdatorServiceTest {

	private static final String JSON = "json";

	private static final String CSV = "csv";

	@Mock
	private ProductService productService;

	@Mock
	private MenuService menuService;

	@InjectMocks
	private DatabaseUpdatorServiceImpl databaseUpdatorServiceImpl;

	@Test
	public void shouldImportProductsFileCSVWhenIsOK() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "name;price;available;categoryId;managerId\nBisdeddzdzedzedzap;0.50;true;2;1".getBytes());
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setPrice(0.50);
		productDTO.setName("Bisdeddzdzedzedzap");
		productDTO.setManagerId(1L);
		productDTO.setCategoryId(2L);
		products.add(productDTO);

		// When
		when(productService.saveAll(anyList())).thenReturn(products);

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, CSV)).isNotEmpty();
	}

	@Test
	public void shouldImportProductsFileCSVWhenIsEmpty() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "name;price;".getBytes());

		// When
		when(productService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportProductsFileCSVWhenIsKO1() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());

		// When
		when(productService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportProductsFileJSONWhenIsOK() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"Tofffedfefeto\",\"price\":0.50,\"available\":true,\"categoryId\":2,\"managerId\":1}]".getBytes());
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setPrice(0.50);
		productDTO.setName("Bisdeddzdzedzedzap");
		productDTO.setManagerId(1L);
		productDTO.setCategoryId(2L);
		products.add(productDTO);

		// When
		when(productService.saveAll(anyList())).thenReturn(products);

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, JSON)).isNotEmpty();
	}

	@Test
	public void shouldImportProductsFileJSONWhenIsEmpty() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"Tofffedfefeto\",\"price\":0.50,\"available\":true,\"cateyId\":2,\"managerId\":1}]".getBytes());
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setPrice(0.50);
		productDTO.setName("Bisdeddzdzedzedzap");
		productDTO.setManagerId(1L);
		productDTO.setCategoryId(2L);
		products.add(productDTO);

		// When
		when(productService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportProductsFileJSONWhenIsKO1() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setPrice(0.50);
		productDTO.setName("Bisdeddzdzedzedzap");
		productDTO.setManagerId(1L);
		productDTO.setCategoryId(2L);
		products.add(productDTO);

		// When
		when(productService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportProductsFileJSONWhenIsKO2() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());

		// When
		when(productService.saveAll(anyList())).thenReturn(null);

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, CSV)).isEmpty();
	}
	
	@Test
	public void shouldImportProductsFileJSONWhenIsKO3() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"Tofffedfefeto\\price\":0.50,\"available\":true,\"cateyId\":2,\"managerId\":1}]".getBytes());
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setPrice(0.50);
		productDTO.setName("Bisdeddzdzedzedzap");
		productDTO.setManagerId(1L);
		productDTO.setCategoryId(2L);
		products.add(productDTO);

		// When
		when(productService.saveAll(anyList())).thenReturn(products);

		// Then
		assertThatThrownBy(() -> databaseUpdatorServiceImpl.importProductsFile(file, JSON))
		.isInstanceOf(JSONException.class);
	}
	
	@Test
	public void shouldImportProductsFileJSONWhenIsKO4() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"TBifebo\",\"price\":0.50,\"available\":true,\"managerId\":1}]".getBytes());
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setPrice(0.50);
		productDTO.setName("Bisdeddzdzedzedzap");
		productDTO.setManagerId(1L);
		productDTO.setCategoryId(2L);
		products.add(productDTO);

		// When
		when(productService.saveAll(anyList())).thenReturn(products);

		// Then
		assertThatThrownBy(() -> databaseUpdatorServiceImpl.importProductsFile(file, JSON))
		.isInstanceOf(BurgerSTerminalException.class);
	}
	
	
	
	@Test
	public void shouldImportMenusFileWhenIsKO3() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"Tofffedfefeto\",\"price\":0.50,\"available\":true,\"cateyId\":2,\"managerId\":1}]".getBytes());

		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(menuDTOs);

		// Then
		assertThatThrownBy(() -> databaseUpdatorServiceImpl.importMenusFile(file, JSON))
		.isInstanceOf(BurgerSTerminalException.class);
	}

	@Test
	public void shouldImportProductsFileOtherWhenIsNull() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());

		// When
		when(productService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importProductsFile(file, "Other")).isNull();
	}

	@Test
	public void shouldImportMenusFileWhenIsOK() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "name;price;available;managerId\nBibi;0.50;true;1".getBytes());

		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(menuDTOs);

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, CSV)).isNotEmpty();
	}


	@Test
	public void shouldImportMenusFileCSVWhenIsEmpty() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "name;price;".getBytes());

		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, CSV)).isEmpty();
	}
	
	@Test
	public void shouldImportMenusFileCSVWhenIsKO1() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());

		// When
		when(menuService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportMenusFileJSONWhenIsOK() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"TBifebo\",\"price\":0.50,\"available\":true,\"managerId\":1}]".getBytes());
		
		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(menuDTOs);

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, JSON)).isNotEmpty();
	}
	
	// Test
	
	@Test
	public void shouldImportMenusFileJSONWhenIsEmpty() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"TBifebo\",\"price\":0.50,\"available\":true,\"managerId\":1}]".getBytes());
		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportMenusFileJSONWhenIsKO1() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());
		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, CSV)).isEmpty();
	}
	
	@Test
	public void shouldImportMenusFileWhenIsKO2() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "[{\"name\":\"Tofffedfefeto\",\"price\":0.50,\"available\":true,\"cateyId\":2,\"managerId\":1}]".getBytes());

		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);

		// When
		when(menuService.saveAll(anyList())).thenReturn(menuDTOs);

		// Then
		assertThatThrownBy(() -> databaseUpdatorServiceImpl.importMenusFile(file, JSON))
		.isInstanceOf(BurgerSTerminalException.class);
	}

	@Test
	public void shouldImportMenusFileJSONWhenIsKO2() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());

		// When
		when(productService.saveAll(anyList())).thenReturn(null);

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, CSV)).isEmpty();
	}

	@Test
	public void shouldImportMenusFileOtherWhenIsNull() throws BurgerSTerminalException {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "".getBytes());

		// When
		when(menuService.saveAll(anyList())).thenReturn(Collections.emptyList());

		// Then
		assertThat(databaseUpdatorServiceImpl.importMenusFile(file, "Other")).isNull();
	}


}
