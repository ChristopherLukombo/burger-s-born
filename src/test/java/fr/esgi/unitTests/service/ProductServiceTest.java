package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Category;
import fr.esgi.domain.Command;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.impl.ProductServiceImpl;
import fr.esgi.service.mapper.ProductMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceTest {

	private static final String STEAK = "steak";

	private static final String PLAT = "PLAT";

	private static final boolean AVAILABLE = true;

	private static final int PRICE = 1;

	private static final String NAME = "NAME";

	private static final String TEST = "TEST";

	private static final long ID = 1L;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

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
	public void shouldFindAllWhenIsOK() {
		// Given 
		List<Product> content = new ArrayList<>();
		content.add(getProduct());
		Page<Product> products = new PageImpl<>(content);

		// When
		when(productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(getProductDTO());

		// Then
		assertThat(productServiceImpl.findAll(0, 10)).isNotEmpty();
	}

	@Test
	public void shouldFindAllWhenIsEmpty() {
		// Given 
		List<Product> content = new ArrayList<>();
		Page<Product> products = new PageImpl<>(content);

		// When
		when(productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(getProductDTO());

		// Then
		assertThat(productServiceImpl.findAll(0, 10)).isEmpty();
	}

	@Test
	public void shouldFindAllWhenIsKO() {
		// Given 
		Page<Product> products = null;
		ProductDTO productDTO = null;

		// When
		when(productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);

		// Then
		assertThatThrownBy(() -> productServiceImpl.findAll(0, 10))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldSaveAllWhenIsOK() {
		// Given 
		List<Product> products = new ArrayList<>();
		products.add(getProduct());

		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(getProductDTO());

		ProductDTO productDTO = getProductDTO();

		// When
		when(productRepository.saveAll(anyList())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);

		// Then
		assertThat(productServiceImpl.saveAll(productDTOs)).isNotEmpty();
	}

	@Test
	public void shouldSaveAllWhenIsKO() {
		// Given 
		List<Product> products = null;

		List<ProductDTO> productDTOs = null;

		ProductDTO productDTO = null;

		// When
		when(productRepository.saveAll(anyList())).thenReturn(products);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);

		// Then
		assertThatThrownBy(() -> productServiceImpl.saveAll(productDTOs))
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldFindProductsByCategoryNameWhenIsOK() {
		// Given
		List<Product> content = new ArrayList<>();
		content.add(getProduct());
		Page<Product> page = new PageImpl<>(content);
		
		ProductDTO productDTO = getProductDTO();
		
		// When
		when(productRepository.findAllByCategoryName((org.springframework.data.domain.Pageable) any(), anyString())).thenReturn(page);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);
		
		// Then
		assertThat(productServiceImpl.findProductsByCategoryName(PageRequest.of(0, 10), PLAT)).isNotEmpty();
	}
	
	@Test
	public void shouldFindProductsByCategoryNameWhenIsEmpty() {
		// Given
		List<Product> content = new ArrayList<>();
		Page<Product> page = new PageImpl<>(content);
		
		ProductDTO productDTO = getProductDTO();
		
		// When
		when(productRepository.findAllByCategoryName((org.springframework.data.domain.Pageable) any(), anyString())).thenReturn(page);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);
		
		// Then
		assertThat(productServiceImpl.findProductsByCategoryName(PageRequest.of(0, 10), PLAT)).isEmpty();
	}
	
	@Test
	public void shouldFindProductsByCategoryNameWhenIsKO() {
		// Given
		Page<Product> page = null;
		ProductDTO productDTO = null;
		
		// When
		when(productRepository.findAllByCategoryName((org.springframework.data.domain.Pageable) any(), anyString())).thenReturn(page);
		when(productMapper.productToProductDTO(((Product) any()))).thenReturn(productDTO);
		
		// Then
		assertThatThrownBy(() -> productServiceImpl.findProductsByCategoryName(PageRequest.of(0, 10), PLAT))
		.isInstanceOf(NullPointerException.class);
	}
	
}
