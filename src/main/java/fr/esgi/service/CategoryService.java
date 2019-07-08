package fr.esgi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CategoryDTO;

/**
 * Service Interface for managing Category.
 * @author mickael
 */
@Service
public interface CategoryService {
	
    /**
     * Get all category
     * @return List<CategoryDTO>
     */
	List<CategoryDTO> findAll();
}
