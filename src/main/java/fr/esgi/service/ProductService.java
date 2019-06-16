package fr.esgi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.esgi.domain.Product;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.dto.UserDTO;

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
     * Save the user in database.
     * @param productDTO
     * @param available
     * @param name
     * @param price
     * @return Product
     */
    ProductDTO addProduct(ProductDTO productDTO, Long id, Boolean available, String name, double price);

    /**
     * Save a Product in the Database.
     * @param productDTO
     * @return
     */
    ProductDTO addProduct(ProductDTO productDTO);
    
    List<Product> findAllByMenuId(Long menuId);

}