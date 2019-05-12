package fr.esgi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Product;
import fr.esgi.service.ProductService;

/**
 * Service Implementation for managing User.
 * @author mickael
 */
@Service("ProductService")
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    
    /**
     * Returns all products
     * @return List<Product>
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
