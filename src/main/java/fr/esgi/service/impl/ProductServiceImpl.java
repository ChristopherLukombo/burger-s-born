package fr.esgi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.ProductRepository;
import fr.esgi.domain.Product;
import fr.esgi.service.ProductService;
import fr.esgi.service.dto.ProductDTO;
import fr.esgi.service.mapper.ProductMapper;

/**
 * Service Implementation for managing Product.
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
	@Override
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(int page, int size) {
		LOGGER.debug("Request to get all products");
		return productRepository.findAll(PageRequest.of(page, size))
				.map(productMapper::productToProductDTO);
	}
	
	/**
	 * Get all products
	 * @return list of entities
	 */
	@Override
	public List<ProductDTO> findAll() {
		LOGGER.debug("Request to get all products");
		return productRepository.findAll().stream()
				.map(productMapper::productToProductDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Save all the products.
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

	/**
	 * Save the product in database.
	 * @param ProductDTO
	 * @return ProductDTO
	 */
	@Override
	public ProductDTO save(ProductDTO productDTO) {
		LOGGER.debug("Request to save a product: {}", productDTO);
		Product product = productMapper.productDTOToProduct(productDTO);
		product = productRepository.save(product);
		return productMapper.productToProductDTO(product);
	}

	/**
	 * Delete the "id" product.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		LOGGER.debug("Request to delete a product");
		productRepository.deleteById(id);
	}

	/**
	 * Update a product.
	 *
	 * @param productDTO the entity to update
	 * @return the persisted entity
	 */
	@Override
	public ProductDTO update(ProductDTO productDTO) {
		LOGGER.debug("Request to update a product: {}", productDTO);
		Product product = productMapper.productDTOToProduct(productDTO);
		product = productRepository.saveAndFlush(product);
		return productMapper.productToProductDTO(product);
	}

    /**
     * Get all the products by Category name.
     * @return  Page<ProductDTO>
     */
	@Override
	@Transactional(readOnly = true)
	public Page<ProductDTO> findProductsByCategoryName(Pageable pageable, String categoryName) {
		LOGGER.debug("Request to find all products by categoryName: {}", categoryName);
		return productRepository.findAllByCategoryName(pageable, categoryName)
				.map(productMapper::productToProductDTO);
	}

	 /**
     * Find all products by menuId.
     * 
     * @param menuId : the id of menu.
     * @return the list of entities.
     */
	@Override
	public List<ProductDTO> findProductsByMenuId(Long menuId) {
		LOGGER.debug("Request to find all products by menuId: {}", menuId);
		return productRepository.findAllByMenuId(menuId).stream()
				.map(productMapper::productToProductDTO)
				.collect(Collectors.toList());
	}
}