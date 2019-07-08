package fr.esgi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.CategoryRepository;
import fr.esgi.service.CategoryService;
import fr.esgi.service.dto.CategoryDTO;
import fr.esgi.service.mapper.CategoryMapper;

/**
 * Service Implementation for managing Category.
 * @author mickael
 */
@Service("CategoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	private final CategoryRepository categoryRepository;
	
	private final CategoryMapper categoryMapper;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	/**
	 * Returns all categories
	 * @return List<Category>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		LOGGER.debug("Request to get all categories");
		return categoryRepository.findAll().stream()
				.map(categoryMapper::categoryToCategoryDTO)
				.collect(Collectors.toList());
	}
}