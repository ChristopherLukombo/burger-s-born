package fr.esgi.unitTests.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import fr.esgi.dao.CategoryRepository;
import fr.esgi.domain.Category;
import fr.esgi.service.CategoryService;
import fr.esgi.service.dto.CategoryDTO;
import fr.esgi.service.impl.CategoryServiceImpl;
import fr.esgi.service.mapper.CategoryMapper;
import fr.esgi.web.handler.RestResponseEntityExceptionHandler;
import fr.esgi.web.rest.CategoryResource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CategoryResourceTest {

	private static final String NAME = "DESSERT";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryMapper categoryMapper;

	@Mock
	private CategoryService categoryService;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private CategoryResource categoryResource;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		initMocks();
		mockMvc = MockMvcBuilders
				.standaloneSetup(categoryResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	private void initMocks() {
		categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
		categoryResource = new CategoryResource(categoryService, messageSource);
	}

	private static Category getCategory() {
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);
		return category;
	}

	private static CategoryDTO getCategoryDTO() {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(ID);
		categoryDTO.setName(NAME);
		return categoryDTO;
	}

	@Test
	public void shouldGetAllCategoryWhenIsOK() throws Exception {
		// Given 
		List<Category> categories = new ArrayList<>();
		categories.add(getCategory());

		// When
		when(categoryRepository.findAll()).thenReturn(categories);
		when(categoryMapper.categoryToCategoryDTO((Category) any())).thenReturn(getCategoryDTO());

		// Then
		mockMvc.perform(get("/api/category")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldGetAllCategoryWhenIsEmpty() throws Exception {
		// Given 
		List<Category> categories = new ArrayList<>();

		// When
		when(categoryRepository.findAll()).thenReturn(categories);

		// Then
		mockMvc.perform(get("/api/category")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound());
	}
}
