package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import fr.esgi.dao.MenuRepository;
import fr.esgi.domain.Category;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.dto.MenuDTO;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.impl.MenuServiceImpl;
import fr.esgi.service.mapper.MenuMapper;
import fr.esgi.service.mapper.ProductMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MenuServiceTest {
	
	private static final String STEAK = "steak";

	private static final String PLAT = "PLAT";

	private static final boolean AVAILABLE = true;

	private static final String NAME = "NAME";
	
	private static final String MENU_TASTY = "Menu Tasty";

	private static final int PRICE = 1;

	private static final String TEST = "TEST";

	private static final long ID = 1L;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private MenuMapper menuMapper;
	
	@Mock
	private ProductMapper productMapper;

	@InjectMocks
	private MenuServiceImpl menuServiceImpl;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		menuServiceImpl = new MenuServiceImpl(menuRepository, menuMapper, productMapper);
	}
	
	private static MenuDTO getMenuDTO() {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(ID);
		menuDTO.setName(MENU_TASTY);
		menuDTO.setPrice(PRICE);
		menuDTO.setAvailable(true);
    	menuDTO.setManagerId(ID);
    	menuDTO.setProductsDTO(Arrays.asList(getProductDTO()));
    	
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
	public void shouldFindAllWhenIsOK() {
		// Given 
		List<Menu> content = new ArrayList<>();
		content.add(getMenu());
		Page<Menu> menus = new PageImpl<>(content);

		// When
		when(menuRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(getMenuDTO());

		// Then
		assertThat(menuServiceImpl.findAll(0, 10)).isNotEmpty();
	}

	@Test
	public void shouldFindAllWhenIsEmpty() {
		// Given 
		List<Menu> content = new ArrayList<>();
		Page<Menu> menus = new PageImpl<>(content);

		// When
		when(menuRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(getMenuDTO());


		// Then
		assertThat(menuServiceImpl.findAll(0, 10)).isEmpty();
	}

	@Test
	public void shouldFindAllWhenIsKO() {
		// Given 
		Page<Menu> menus = null;
		MenuDTO menuDTO = null;

		// When
		when(menuRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);


		// Then
		assertThatThrownBy(() -> menuServiceImpl.findAll(0, 10))
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldFindAllTrendsMenusWhenIsOK() {
		// Given 
		List<Menu> menus = new ArrayList<>();
		menus.add(getMenu());

		// When
		when(menuRepository.findAllTrendsMenus(anyString(), anyInt())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(getMenuDTO());

		// Then
		assertThat(menuServiceImpl.findAllTrendsMenus()).isNotEmpty();
	}
	
	@Test
	public void shouldFindAllTrendsMenusWhenIsKO() {
		// Given 
		List<Menu> menus = null;
		MenuDTO menuDTO = null;

		// When
		when(menuRepository.findAllTrendsMenus(anyString(), anyInt())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);

		// Then
		assertThatThrownBy(() -> menuServiceImpl.findAllTrendsMenus())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldFindAllTrendsMenusWhenIsEmpty() {
		// Given 
		List<Menu> menus = new ArrayList<>();
		MenuDTO menuDTO = getMenuDTO();

		// When
		when(menuRepository.findAllTrendsMenus(anyString(), anyInt())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);

		// Then
		assertThat(menuServiceImpl.findAllTrendsMenus()).isEmpty();
	}
	
	@Test
	public void shouldSaveAllWhenIsOK() {
		// Given 
		List<Menu> menus = new ArrayList<>();
		menus.add(getMenu());

		List<MenuDTO> menusDTOs = new ArrayList<>();
		menusDTOs.add(getMenuDTO());

		MenuDTO menuDTO = getMenuDTO();

		// When
		when(menuRepository.saveAll(anyList())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);

		// Then
		assertThat(menuServiceImpl.saveAll(menusDTOs)).isNotEmpty();
	}

	@Test
	public void shouldSaveAllWhenIsKO() {
		// Given 
		List<Menu> menus = null;

		List<MenuDTO> menusDTOs = null;

		MenuDTO menuDTO = null;

		// When
		when(menuRepository.saveAll(anyList())).thenReturn(menus);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);

		// Then
		assertThatThrownBy(() -> menuServiceImpl.saveAll(menusDTOs))
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldFindProductsByMenuIdWhenIsOK() {
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
		assertThat(menuServiceImpl.findProductsByMenuId(ID, PLAT)).isNotEmpty();
	}
	
	@Test
	public void shouldFindProductsByMenuIdWhenProductIsEmpty() {
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
		assertThat(menuServiceImpl.findProductsByMenuId(ID, STEAK)).isEmpty();
	}
	
	@Test
	public void shouldFindProductsByMenuIdWhenProductIsKO() {
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
		assertThat(menuServiceImpl.findProductsByMenuId(ID, STEAK)).isEmpty();
	}
	
}
