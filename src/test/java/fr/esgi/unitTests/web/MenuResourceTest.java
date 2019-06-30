//package fr.esgi.unitTests.web;
//
//import static fr.esgi.unitTests.web.TestUtil.convertObjectToJsonBytes;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import fr.esgi.dao.ManagerRepository;
//import fr.esgi.dao.MenuRepository;
//import fr.esgi.dao.ProductRepository;
//import fr.esgi.service.MenuService;
//import fr.esgi.service.dto.MenuDTO;
//import fr.esgi.service.impl.MenuServiceImpl;
//import fr.esgi.service.mapper.MenuMapper;
//import fr.esgi.service.mapper.ProductMapper;
//import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
//import fr.esgi.web.rest.MenuResource;
//
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//public class MenuResourceTest {
//
//	private static final String TEST = "TEST";
//
//	private static final long ID = 1L;
//
//	private MockMvc mockMvc;
//
//	@Mock
//	private MenuRepository menuRepository;
//
//	@Mock
//	private MenuMapper menuMapper;
//
//	@Mock
//	private MenuService menuService;
//
//	@Mock
//	private ManagerRepository managerRepository;
//
//	@Mock
//	private ProductRepository productRepository;
//
//	@Mock
//	private ProductMapper productMapper;
//
//	@InjectMocks
//	private MenuResource menuResource;
//
//	@Before
//	public void init(){
//		MockitoAnnotations.initMocks(this);
//		initMocks();
//		mockMvc = MockMvcBuilders
//				.standaloneSetup(menuResource).setControllerAdvice(new RestResponseEntityExceptionHandler())
//				.build();
//	}
//
//	private void initMocks() {
//		menuService = new MenuServiceImpl(menuRepository, managerRepository, productRepository, menuMapper, productMapper);
//		menuResource = new MenuResource(menuService);
//	}
//
//	private static MenuDTO getMenuDTO() {
//		MenuDTO menuDTO = new MenuDTO();
//		menuDTO.setId(ID);
//		menuDTO.setName(TEST);
//		menuDTO.setPrice(1);
//		menuDTO.setAvailable(true);		
//		menuDTO.setManagerId(1L);
//		return menuDTO;
//	}
//
//	@Test
//	public void shouldGetAllTrendsMenusWhenIsOK() throws IOException, Exception {
//       // Given
//		List<MenuDTO> menusDTO = new ArrayList<>();
//		menusDTO.add(getMenuDTO());
//		
//		// When
//		when(menuService.findAllTrendsMenus()).thenReturn(menusDTO);
//		mockMvc.perform(get("/api/menus/trends")
//								.contentType(TestUtil.APPLICATION_JSON_UTF8)
//								.content(convertObjectToJsonBytes(menusDTO)))
//						.andExpect(status().isOk());
//		
//		// Then
//		assertThat(menuService.findAllTrendsMenus()).isEqualTo(menusDTO);
//	}
//}
