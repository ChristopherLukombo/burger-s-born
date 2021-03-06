package fr.esgi.unitTests.web;

import static fr.esgi.unitTests.web.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.esgi.config.ConfigurationService;
import fr.esgi.domain.Category;
import fr.esgi.domain.Command;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Menu;
import fr.esgi.domain.Product;
import fr.esgi.service.CommandService;
import fr.esgi.service.PayPalService;
import fr.esgi.service.dto.CommandDTO;
import fr.esgi.service.impl.PayPalServiceImpl;
import fr.esgi.service.mapper.CommandMapper;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.PayPalResource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PaypalResourceTest {
	
	private static final String PLAT = "PLAT";

	private static final boolean AVAILABLE = true;

	private static final String NAME = "NAME";

	private static final int PRICE = 1;

	private static final String MENU_TASTY = "Menu Tasty";

	private static final String TEST = "TEST";

	private static final long ID = 1L;
	
	private MockMvc mockMvc;
	
	@Mock
	private CommandService commandService;

	@Mock
	private CommandMapper commandMapper;

	@Mock
	private ConfigurationService configurationService;

	@Mock
	private PayPalService payPalService;
	
	@Mock
	private MessageSource messageSource;
	
	@InjectMocks
	private PayPalResource payPalResource;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(payPalResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		payPalService = new PayPalServiceImpl(commandService, commandMapper, configurationService);
		payPalResource = new PayPalResource(payPalService, messageSource);
	}
	
	private static CommandDTO getCommandDTO() {
		CommandDTO commandDTO = new CommandDTO();
		commandDTO.setId(ID);
		commandDTO.setOrderStatus(TEST);
		commandDTO.setDate(ZonedDateTime.now());
		commandDTO.setPaymentId(TEST);
		commandDTO.setPrice(new BigDecimal(1));
		commandDTO.setSaleId(TEST);
		return commandDTO;
	}

	private static Command getCommand() {
		Command command = new Command();
		command.setId(ID);
		command.setOrderStatus(TEST);
		command.setDate(ZonedDateTime.now());
		command.setPaymentId(TEST);
		command.setPrice(new BigDecimal(1));
		command.setSaleId(TEST);
		command.setMenus(getMenus());
		command.setProducts(Arrays.asList(getProduct()));
		return command;
	}

	private static List<Menu> getMenus() {
		List<Menu> menus = new ArrayList<>();
		Menu menu = new Menu();
		menu.setId(ID);
		menu.setName(MENU_TASTY);
		menu.setPrice(1);
		menu.setAvailable(true);
		menu.setManager(getManager());
		menu.setProducts(Arrays.asList(getProduct()));
		menus.add(menu);
		return menus;
	}

	private static Manager getManager() {
		Manager manager = new Manager();
		manager.setId(ID);
		return manager;
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

//	private static ProductDTO getProductDTO() {
//		ProductDTO productDTO = new ProductDTO();
//		productDTO.setId(ID);
//		productDTO.setName(NAME);
//		productDTO.setPrice(PRICE);
//		productDTO.setAvailable(AVAILABLE);
//		productDTO.setCategoryId(ID);
//		productDTO.setManagerId(ID);
//
//		return productDTO;
//	}

	@Test
	public void shouldCreatePaymentWhenIsKO() throws Exception {
		// Given
		CommandDTO commandDTO  = getCommandDTO();

		// When
		when(commandMapper.commandDTOToCommand((CommandDTO) any())).thenReturn(getCommand());
		when(configurationService.getPaypalClientId()).thenReturn("test");
		when(configurationService.getPaypalClientSecret()).thenReturn("test");

		// Then
		mockMvc.perform(post("/api/make/payment")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(commandDTO)))
		.andExpect(status().isOk());
	}
}
