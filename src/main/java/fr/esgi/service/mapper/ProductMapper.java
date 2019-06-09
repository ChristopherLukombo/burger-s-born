package fr.esgi.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.esgi.domain.Product;
import fr.esgi.service.dto.ProductDTO;

/**
 * Mapper for the entity Product and its DTO called ProductDTO.
 * @author christopher
 */
@Mapper(uses = { ProductDTO.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "available", target = "available"),
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "manager.id", target = "managerId"),
    })
    ProductDTO productToProductDTO(Product product);

    @InheritInverseConfiguration
    Product productDTOToProduct(ProductDTO productDTO) ;

}
