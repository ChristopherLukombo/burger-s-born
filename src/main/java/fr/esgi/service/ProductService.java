package fr.esgi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.esgi.service.dto.CommandDTO;
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
	 * Add product.
	 * 
	 * @param productDTO of entities to save
	 * @return the entities saved
	 */
    ProductDTO addProduct(ProductDTO product);
    
    /**
	 * Delete product.
	 * 
	 * @param id the id of the entity
	 */
    void delete(Long id);
    
    
    /**
	 * Update a product.
	 *
	 * @param productDTO the entity to update
	 * @return the persisted entity
	 */
	ProductDTO update(ProductDTO productDTO);
    
    
}