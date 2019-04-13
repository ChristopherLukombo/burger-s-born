package fr.esgi.service.mapper;

import fr.esgi.domain.Category;
import fr.esgi.service.dto.CategoryDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity Category and its DTO called CategoryDTO.
 * @author christopher
 */
@Mapper(uses = { CategoryDTO.class }, componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    CategoryDTO categoryToCategoryDTO(Category category);

    @InheritInverseConfiguration
    Category categoryDTOToCategory(CategoryDTO categoryDTO) ;

}
