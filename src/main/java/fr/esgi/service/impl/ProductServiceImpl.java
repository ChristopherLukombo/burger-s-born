package fr.esgi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import fr.esgi.dao.ProductRepository;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.mapper.ProductMapper;

/**
 * Service Implementation for managing User.
 * @author mickael
 */
@Service("ProductService")
@Transactional
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	 
    private final ProductRepository productRepository;
 
    private final ProductMapper productMapper;
 
    @Autowired(required = true)    
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
 
    /**
     * Returns all products
     * @return List<Product>
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(int page, int size) {
        LOGGER.debug("Request to get all products");
        return productRepository.findAll(PageRequest.of(page, size))
                .map(productMapper::productToProductDTO);
    }

	@Override
	public ProductDTO addProduct(ProductDTO productDTO, Long id, Boolean available, String name, double price) {
		LOGGER.debug("Request to add new product");
		return null;
	}

}