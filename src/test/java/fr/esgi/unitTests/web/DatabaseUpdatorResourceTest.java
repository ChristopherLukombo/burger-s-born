package fr.esgi.unitTests.web;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.esgi.service.DatabaseUpdatorService;
import fr.esgi.service.MenuService;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.impl.DatabaseUpdatorServiceImpl;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.DatabaseUpdatorResource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DatabaseUpdatorResourceTest {
	
	private static final boolean AVAILABLE = true;

	private static final double PRICE = 0.50;

	private static final String NAME = "nBisdeddzdzedzedzap";

	private static final long ID = 1L;

	private MockMvc mockMvc;
	
	@Mock
	private ProductService productService;

	@Mock
	private MenuService menuService;
	
	@Mock
	private DatabaseUpdatorService databaseUpdatorService;

	@Mock
	private MessageSource messageSource;
	
	@InjectMocks
	private DatabaseUpdatorResource databaseUpdatorResource;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(databaseUpdatorResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		databaseUpdatorService = new DatabaseUpdatorServiceImpl(productService, menuService);
		databaseUpdatorResource = new DatabaseUpdatorResource(databaseUpdatorService, messageSource);
	}
	
	private static ProductDTO getProductDTO() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(ID);
		productDTO.setName(NAME);
		productDTO.setPrice(PRICE);
		productDTO.setAvailable(AVAILABLE);
		productDTO.setCategoryId(2L);
		productDTO.setManagerId(1L);

		return productDTO;
	}
	
	private static MenuDTO getMenuDTO() {
		List<MenuDTO> menuDTOs = new ArrayList<>();
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(1L);
		menuDTO.setName("Bibi");
		menuDTO.setPrice(0.50);
		menuDTO.setAvailable(true);
		menuDTO.setManagerId(1L);
		menuDTOs.add(menuDTO);
		return menuDTO;
	}
	
	@Test
	public void shouldUploadProductsFileCSVWhenIsOK() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", "name;price;available;categoryId;managerId\nBisdeddzdzedzedzap;0.50;true;2;1".getBytes());
		
		List<ProductDTO> products = new ArrayList<>();
		products.add(getProductDTO());

		MockPart part = new MockPart("importfile", "filename.csv", file.getBytes());
		
		// When
		when(productService.saveAll(anyList())).thenReturn(products);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/import?fileFormat=csv")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void shouldUploadProductsFileCSVWhenIsKO1() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", "name;price;available;categoryId;managerId\nBisdeddzdzedzedzap;0.50;true;2;1".getBytes());
		
		List<ProductDTO> products = Collections.emptyList();

		MockPart part = new MockPart("importfile", "filename.txt", file.getBytes());
		
		// When
		when(productService.saveAll(anyList())).thenReturn(products);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/import?fileFormat=txt")
				.part(part))
		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldUploadProductsFileCSVWhenIsEmpty() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", "name;price;avaianager".getBytes());
		
		List<ProductDTO> products = Collections.emptyList();

		MockPart part = new MockPart("importfile", "filename.csv", file.getBytes());
		
		// When
		when(productService.saveAll(anyList())).thenReturn(products);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/import?fileFormat=csv")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void shouldUploadProductsFileJSONWhenIsOK() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.json", "text/plain", "[{\"name\":\"Tofffedfefeto\",\"price\":0.50,\"available\":true,\"categoryId\":2,\"managerId\":1}]".getBytes());
		
		List<ProductDTO> products = new ArrayList<>();
		products.add(getProductDTO());

		MockPart part = new MockPart("importfile", "filename.json", file.getBytes());
		
		// When
		when(productService.saveAll(anyList())).thenReturn(products);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/import?fileFormat=json")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void shouldUploadProductsFileJSONWhenIsKO() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.json", "text/plain", "[{\"name\":\"Tofffedfefeto\",\"price\":0.50,\"available\":true,\"categoryId\":2,\"managerId\":1}]".getBytes());
		
		List<ProductDTO> products = Collections.emptyList();

		MockPart part = new MockPart("importfile", "filename.json", file.getBytes());
		
		// When
		when(productService.saveAll(anyList())).thenReturn(products);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/import?fileFormat=json")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty());
	}
	// Menu
	
	
	@Test
	public void shouldUploadMenusFileCSVWhenIsOK() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", "name;price;available;managerId\nBibi;0.50;true;1".getBytes());
		
		List<MenuDTO> menus = new ArrayList<>();
		menus.add(getMenuDTO());

		MockPart part = new MockPart("importfile", "filename.csv", file.getBytes());
		
		// When
		when(menuService.saveAll(anyList())).thenReturn(menus);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/menu/import?fileFormat=csv")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void shouldUploadMenusFileCSVWhenIsKO1() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", "name;price;available;categoryId;managerId\nBisdeddzdzedzedzap;0.50;true;2;1".getBytes());
		
		List<MenuDTO> menus = Collections.emptyList();

		MockPart part = new MockPart("importfile", "filename.txt", file.getBytes());
		
		// When
		when(menuService.saveAll(anyList())).thenReturn(menus);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/menu/import?fileFormat=csv")
				.part(part))
		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void shouldUploadMenusFileCSVWhenIsEmpty() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", "name;price;avaianager".getBytes());
		
		List<MenuDTO> menus = Collections.emptyList();

		MockPart part = new MockPart("importfile", "filename.csv", file.getBytes());
		
		// When
		when(menuService.saveAll(anyList())).thenReturn(menus);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/menu/import?fileFormat=csv")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void shouldUploadMenusFileJSONWhenIsOK() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.json", "text/plain", "[{\"name\":\"TBifebo\",\"price\":0.50,\"available\":true,\"managerId\":1}]".getBytes());
		
		List<MenuDTO> menus = new ArrayList<>();
		menus.add(getMenuDTO());

		MockPart part = new MockPart("importfile", "filename.json", file.getBytes());
		
		// When
		when(menuService.saveAll(anyList())).thenReturn(menus);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/menu/import?fileFormat=json")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void shouldUploadMenusFileJSONWhenIsKO() throws Exception {
		// Given
		MockMultipartFile file = new MockMultipartFile("file", "filename.json", "text/plain", "[{\"name\":\"TBifebo\",\"price\":0.50,\"available\":true,\"managerId\":1}]".getBytes());
		
		List<MenuDTO> menus = Collections.emptyList();

		MockPart part = new MockPart("importfile", "filename.json", file.getBytes());
		
		// When
		when(menuService.saveAll(anyList())).thenReturn(menus);
		
		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/menu/import?fileFormat=json")
				.part(part))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isEmpty());
	}
	

}
