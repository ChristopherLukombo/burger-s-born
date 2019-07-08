package fr.esgi.unitTests.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.esgi.dao.CategoryRepository;
import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Category;
import fr.esgi.domain.Command;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.impl.ProductServiceImpl;
import fr.esgi.service.mapper.ProductMapper;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.ProductResource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductResourceTest {
	
	private static final String STEAK = "steak";

	private static final String PLAT = "PLAT";

	private static final boolean AVAILABLE = true;

	private static final int PRICE = 1;

	private static final String NAME = "NAME";

	private static final String TEST = "TEST";

	private static final long ID = 1L;
	
	private MockMvc mockMvc;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ProductMapper productMapper;
	
	@Mock 
	private CategoryRepository categoryRepository;

	@Mock
	private ProductService productService;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private ProductResource productResource;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(productResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		productService = new ProductServiceImpl(productRepository, productMapper, categoryRepository);
		productResource = new ProductResource(productService, messageSource);
	}
	
	private static ProductDTO getProductDTO() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(ID);
		productDTO.setName(NAME);
		productDTO.setPrice(PRICE);
		productDTO.setAvailable(AVAILABLE);
		productDTO.setCategoryId(ID);
		productDTO.setManagerId(ID);

		return productDTO;
	}

	private static Product getProduct() {
		Product product = new Product();
		product.setId(ID);
		product.setName(NAME);
		product.setIngredients((List<String>) Arrays.asList("Sucre", "Farine"));
		product.setPrice(PRICE);
		product.setAvailable(AVAILABLE);
		product.setCategory(getCategory());
		product.setMenus((List<Menu>) Arrays.asList(getMenu()));
		product.setManager(getManager());
		product.setCommands((List<Command>) Arrays.asList(getCommand()));

		return product;
	}

	private static Menu getMenu() {
		Menu menu = new Menu();
		menu.setId(ID);
		menu.setName(STEAK);
		return menu;
	}

	private static Manager getManager() {
		Manager manager = new Manager();
		manager.setId(1L);
		return manager;
	}

	private static Category getCategory() {
		Category category = new Category();
		category.setId(ID);
		category.setName(PLAT);
		return category;
	}

	private static Command getCommand() {
		Command command = new Command();
		command.setId(ID);
		command.setOrderStatus(TEST);
		command.setDate(ZonedDateTime.now());
		command.setPaymentId(TEST);
		command.setPrice(new BigDecimal(1));
		command.setSaleId(TEST);
		return command;
	}
	
	@Test
	public void shouldFindAllWhenIsOK() throws Exception {
		// Given 
		List<Product> content = new ArrayList<>();
		content.add(getProduct());
		Page<Product> products = new PageImpl<>(content);

		// When
		when(productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(getProductDTO());

		// Then
		mockMvc.perform(get("/api/products?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldFindAllWhenIsEmpty() throws Exception {
		// Given 
		List<Product> content = new ArrayList<>();
		Page<Product> products = new PageImpl<>(content);

		// When
		when(productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(getProductDTO());

		// Then
		mockMvc.perform(get("/api/products?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldFindProductsByCategoryNameWhenIsOK() throws Exception {
		// Given
		List<Product> content = new ArrayList<>();
		content.add(getProduct());
		Page<Product> page = new PageImpl<>(content);
		
		ProductDTO productDTO = getProductDTO();
		
		// When
		when(productRepository.findAllByCategoryName((org.springframework.data.domain.Pageable) any(), anyString())).thenReturn(page);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);
		
		// Then
		mockMvc.perform(get("/api/products/category?page=0&size=10&categorieName=plat")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldFindProductsByCategoryNameWhenIsEmpty() throws Exception {
		// Given
		List<Product> content = new ArrayList<>();
		Page<Product> page = new PageImpl<>(content);
		
		ProductDTO productDTO = getProductDTO();
		
		// When
		when(productRepository.findAllByCategoryName((org.springframework.data.domain.Pageable) any(), anyString())).thenReturn(page);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);
		
		// Then
		mockMvc.perform(get("/api/products/category?page=0&size=10&categorieName=plat")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
}
