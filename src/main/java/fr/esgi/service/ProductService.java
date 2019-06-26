package fr.esgi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.esgi.domain.Product;
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
    
    List<Product> findAllByMenuId(Long menuId);
    
    /**
	 * SaveAll products.
	 * 
	 * @param productsDTO the list of entities to save
	 * @return the list of entities
	 */
    List<ProductDTO> saveAll(List<ProductDTO> products);
}