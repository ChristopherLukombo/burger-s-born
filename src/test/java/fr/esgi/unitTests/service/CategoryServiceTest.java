package fr.esgi.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.esgi.dao.CategoryRepository;
import fr.esgi.domain.Category;
import fr.esgi.service.dto.CategoryDTO;
import fr.esgi.service.impl.CategoryServiceImpl;
import fr.esgi.service.mapper.CategoryMapper;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
	
	private static final String NAME = "DESSERT";

	private static final long ID = 1L;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryMapper categoryMapper;

	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

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
		assertThat(categoryServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllCategoryWhenIsEmpty() throws Exception {
		// Given 
		List<Category> categories = new ArrayList<>();

		// When
		when(categoryRepository.findAll()).thenReturn(categories);

		// Then
		assertThat(categoryServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllCategoryWhenIsKO() throws Exception {
		// Given 
		List<Category> categories = null;

		// When
		when(categoryRepository.findAll()).thenReturn(categories);

		// Then
		assertThatThrownBy(() -> categoryServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
}
