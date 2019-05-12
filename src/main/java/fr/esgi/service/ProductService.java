package fr.esgi.service;

import fr.esgi.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

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
    List<Product> findAll();

}
