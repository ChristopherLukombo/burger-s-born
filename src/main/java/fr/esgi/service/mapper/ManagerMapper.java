package fr.esgi.service.mapper;

import fr.esgi.domain.Manager;
import fr.esgi.service.dto.ManagerDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity Manager and its DTO called ManagerDTO.
 * @author christopher
 */
@Mapper(uses = { ManagerDTO.class }, componentModel = "spring")
public interface ManagerMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "user.id", target = "userId"),
    })
    ManagerDTO managerToManagerDTO(Manager manager);

    @InheritInverseConfiguration
    Manager managerDTOToManager(ManagerDTO managerDTO) ;

}
