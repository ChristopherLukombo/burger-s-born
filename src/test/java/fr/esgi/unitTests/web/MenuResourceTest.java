package fr.esgi.unitTests.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import fr.esgi.dao.ManagerRepository;
import fr.esgi.dao.MenuRepository;
import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Category;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.MenuService;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.impl.MenuServiceImpl;
import fr.esgi.service.mapper.MenuMapper;
import fr.esgi.service.mapper.ProductMapper;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.MenuResource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MenuResourceTest {

	private static final String TEST = "TEST";

	private static final String MENU_TASTY = "Menu Tasty";

	private static final String PLAT = "PLAT";

	private static final boolean AVAILABLE = true;

	private static final String NAME = "NAME";

	private static final int PRICE = 1;

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private MenuMapper menuMapper;

	@Mock
	private MenuService menuService;

	@Mock
	private ManagerRepository managerRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;
	
	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private MenuResource menuResource;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(menuResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		menuService = new MenuServiceImpl(menuRepository, menuMapper, productMapper, productRepository);
		menuResource = new MenuResource(menuService, messageSource);
	}

	private static MenuDTO getMenuDTO() {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(ID);
		menuDTO.setName(TEST);
		menuDTO.setPrice(1);
		menuDTO.setAvailable(true);		
		menuDTO.setManagerId(1L);
		return menuDTO;
	}

	private static Menu getMenu() {
		Menu menu = new Menu();
		menu.setId(ID);
		menu.setName(MENU_TASTY);
		menu.setPrice(1);
		menu.setAvailable(true);
		menu.setManager(getManager());
		menu.setProducts(Arrays.asList(getProduct()));
		return menu;
	}

	private static Manager getManager() {
		Manager manager = new Manager();
		manager.setId(ID);
		return manager;
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

		return product;
	}

	private static Category getCategory() {
		Category category = new Category();
		category.setId(1L);
		category.setName(PLAT);
		return category;
	}
	
	@Test
	public void shouldFindAllWhenIsOK() throws Exception {
		// Given 
		List<Menu> content = new ArrayList<>();
		content.add(getMenu());
		Page<Menu> menus = new PageImpl<>(content);

		// When
		when(menuRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(getMenuDTO());

		// Then
		mockMvc.perform(get("/api/menu/all?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldFindAllWhenIsEmpty() throws Exception {
		// Given 
		List<Menu> content = new ArrayList<>();
		Page<Menu> menus = new PageImpl<>(content);

		// When
		when(menuRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(getMenuDTO());


		// Then
		mockMvc.perform(get("/api/menu/all?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}

	@Test
	public void shouldGetAllTrendsMenusWhenIsOK() throws IOException, Exception {
		// Given
		List<Menu> menus = new ArrayList<>();
		menus.add(getMenu());

		// When
		when(menuRepository.findAllTrendsMenus(anyString(), anyInt())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(getMenuDTO());

		// Then
		mockMvc.perform(get("/api/menus/trends")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldGetAllTrendsMenusWhenIsEmpty() throws IOException, Exception {
		// Given
		List<Menu> menus = new ArrayList<>();

		// When
		when(menuRepository.findAllTrendsMenus(anyString(), anyInt())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(null);

		// Then
		mockMvc.perform(get("/api/menus/trends")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldFindProductsByMenuIdWhenIsOK() throws Exception {
		// Given 
		List<Menu> menus = new ArrayList<>();
		menus.add(getMenu());

		List<MenuDTO> menusDTOs = new ArrayList<>();
		menusDTOs.add(getMenuDTO());

		MenuDTO menuDTO = getMenuDTO();

		// When
		when(menuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(getMenu()));
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);
		when(productMapper.productToProductDTO((Product) any())).thenReturn(getProductDTO());

		// Then
		mockMvc.perform(get("/api/menus/products?id=1&categorieName=PLAT")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldFindProductsByMenuIdWhenProductIsEmpty() throws Exception {
		// Given 
		List<Menu> menus = new ArrayList<>();
		Menu menu = getMenu();
		menu.setProducts(Collections.emptyList());
		menus.add(menu);

		List<MenuDTO> menusDTOs = new ArrayList<>();
		menusDTOs.add(getMenuDTO());

		MenuDTO menuDTO = getMenuDTO();

		// When
		when(menuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(menu));
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);
		when(productMapper.productToProductDTO((Product) any())).thenReturn(getProductDTO());

		// Then
		mockMvc.perform(get("/api/menus/products?id=1&categorieName=dessert")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldFindProductsByMenuIdWhenProductIsKO() throws Exception {
		// Given 
		List<Menu> menus = new ArrayList<>();
		Menu menu = getMenu();
		menu.setProducts(null);
		menus.add(menu);

		List<MenuDTO> menusDTOs = new ArrayList<>();
		menusDTOs.add(getMenuDTO());

		MenuDTO menuDTO = getMenuDTO();

		// When
		when(menuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(menu));
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);
		when(productMapper.productToProductDTO((Product) any())).thenReturn(getProductDTO());

		// Then
		mockMvc.perform(get("/api/menus/products?id=1&categorieName=dessert")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
}
