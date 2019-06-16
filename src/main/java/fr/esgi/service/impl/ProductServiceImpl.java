package fr.esgi.service.impl;

import fr.esgi.dao.CategoryRepository;
import fr.esgi.dao.ManagerRepository;
import fr.esgi.domain.Category;
import fr.esgi.domain.Manager;
import fr.esgi.domain.Product;
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

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing User.
 * @author mickael
 */
@Service("ProductService")
@Transactional
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	 
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ManagerRepository managerRepository;
 
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository, ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
        this.categoryRepository = categoryRepository;
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

    // TODO refactorer la methode
    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.setId(productDTO.getId());
        newProduct.setName(productDTO.getName());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setAvailable(productDTO.isAvailable());
        final Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        if (category.isPresent()) {
            newProduct.setCategory(category.get());
        }
        final Optional<Manager> manager = managerRepository.findById(productDTO.getManagerId());
        if (category.isPresent()) {
            newProduct.setManager(manager.get());
        }

        newProduct = productRepository.save(newProduct);

        LOGGER.debug("Created Information for Product: {}", newProduct);
        return productMapper.productToProductDTO(newProduct);
    }


	@Override
	public ProductDTO addProduct(ProductDTO productDTO, Long id, Boolean available, String name, double price) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Product> findAllByMenuId(Long menuId) {
		return productRepository.findAllByMenuId(menuId);
	}

}