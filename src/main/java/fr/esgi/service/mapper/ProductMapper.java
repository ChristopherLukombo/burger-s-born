//package fr.esgi.service.mapper;
//
//import fr.esgi.domain.Product;
//import fr.esgi.domain.User;
//import fr.esgi.service.dto.ProductDTO;
//import fr.esgi.service.dto.UserDTO;
//import org.mapstruct.*;
//
///**
// * Mapper for the entity Product and its DTO called ProductDTO.
// * @author christopher
// */
//@Mapper(uses = { ProductDTO.class }, componentModel = "spring")
//public interface ProductMapper {
//
//    @Mappings({
//            @Mapping(source = "id", target = "id"),
//            @Mapping(source = "name", target = "name"),
//            @Mapping(source = "price", target = "price"),
//            @Mapping(source = "isAvailable", target = "isAvailable"),
//            @Mapping(source = "category.id", target = "categoryId"),
//            @Mapping(source = "manager.id", target = "managerId"),
//    })
//    ProductDTO productToProductDTO(Product product);
//
//    @InheritInverseConfiguration
//    Product productDTOToProduct(ProductDTO productDTO) ;
//
//}
