package fr.esgi.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.esgi.domain.Category;
import fr.esgi.service.dto.CategoryDTO;

/**
 * Mapper for the entity Category and its DTO called CategoryDTO.
 * @author christopher
 */
@Mapper(uses = { CategoryDTO.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    CategoryDTO categoryToCategoryDTO(Category category);

    @InheritInverseConfiguration
    Category categoryDTOToCategory(CategoryDTO categoryDTO) ;

}
