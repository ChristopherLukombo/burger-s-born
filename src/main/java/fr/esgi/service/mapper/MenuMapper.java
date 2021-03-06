package fr.esgi.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.esgi.domain.Menu;
import fr.esgi.service.dto.MenuDTO;

/**
 * Mapper for the entity Menu and its DTO called MenuDTO.
 * @author christopher
 */
@Mapper(uses = { MenuDTO.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(MenuMapperDecorator.class)
public interface MenuMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "available", target = "available"),
            @Mapping(source = "manager.id", target = "managerId"),
    })
    MenuDTO menuToMenuDTO(Menu menu);

    @InheritInverseConfiguration
    Menu menuDTOToMenu(MenuDTO menuDTO) ;

}
