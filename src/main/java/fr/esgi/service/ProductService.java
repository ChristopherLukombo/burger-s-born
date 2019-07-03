package fr.esgi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.esgi.service.dto.ProductDTO;

/**
 * Service Interface for managing Product.
 * @author mickael
 */
@Service
public interface ProductService {
	
    /**
     * Get all products
     * @return Page<Product>
     */
	Page<ProductDTO> findAll(int page, int size);
    
    /**
	 * SaveAll products.
	 * 
	 * @param productsDTO the list of entities to save
	 * @return the list of entities
	 */
    List<ProductDTO> saveAll(List<ProductDTO> products);
    
    /**
     * Find all products by CategoryName.
     * 
     * @param pageable
     * @param categoryName
     * @return
     */
    Page<ProductDTO> findProductsByCategoryName(Pageable pageable, String categoryName);
}