package fr.esgi.service.mapper;

import fr.esgi.domain.CommandCompo;
import fr.esgi.service.dto.CommandCompoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity CommandCompo and its DTO called CommandCompoDTO.
 * @author christopher
 */
@Mapper(uses = { CommandCompoDTO.class }, componentModel = "spring")
public interface CommandCompoMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "state", target = "state"),
            @Mapping(source = "command.id", target = "commandId"),
            @Mapping(source = "employee.id", target = "employeeId"),
    })
    CommandCompoDTO commandCompoToCommandCompoDTO(CommandCompo CommandCompo);

    @InheritInverseConfiguration
    CommandCompo commandCompoDTOToCommandCompo(CommandCompoDTO CommandCompoDTO) ;

}
