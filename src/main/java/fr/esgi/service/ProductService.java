package fr.esgi.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
 
import fr.esgi.service.dto.ProductDTO;

/**
 * Service Interface for managing Product.
 * @author mickael
 */
@Service
public interface ProductService {
	
    /**
     * Returns products
     * @return List<Product>
     */
	Page<ProductDTO> findAll(int page, int size);

    /**
     * Save a Product in the Database.
     * @param productDTO
     * @return
     */
    ProductDTO addProduct(ProductDTO productDTO);

}