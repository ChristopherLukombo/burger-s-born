package fr.esgi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Product;
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

	@Autowired
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

	/**
	 * SaveAll products.
	 * 
	 * @param productsDTO the list of entities to save
	 * @return the list of entities
	 */
	@Override
	public List<ProductDTO> saveAll(List<ProductDTO> productsDTO) {
		LOGGER.debug("Request to save all products");
		List<Product> products = productsDTO.stream()
				.map(productMapper::productDTOToProduct)
				.collect(Collectors.toList());

		return productRepository.saveAll(products).stream()
				.map(productMapper::productToProductDTO)
				.collect(Collectors.toList());
	}
}