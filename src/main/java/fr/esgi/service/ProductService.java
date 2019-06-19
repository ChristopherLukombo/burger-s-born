package fr.esgi.service;

import fr.esgi.domain.Product;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import fr.esgi.domain.Product;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

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
     * Save the product in database.
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
     * @return ProductDTO added product
     */
    ProductDTO addProduct(ProductDTO productDTO);
    
    List<Product> findAllByMenuId(Long menuId);

    /**
     * Convert a JSONArray frome the imported file to a ArrayList of ProductDTO
     *
     * @param objects
     * @return List<ProductDTO> converted list of products
     */
    List<ProductDTO> convertJsonToArray(JSONArray objects);

    List<ProductDTO> addAll(List<ProductDTO> products);
}