package fr.esgi.service.mapper;

import fr.esgi.domain.Command;
import fr.esgi.service.dto.CommandDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity Command and its DTO called CommandDTO.
 * @author christopher
 */
@Mapper(uses = { CommandDTO.class }, componentModel = "spring")
public interface CommandMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "orderStatus", target = "orderStatus"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "customer.id", target = "customerId"),
    })
    CommandDTO commandToCommandDTO(Command command);

    @InheritInverseConfiguration
    Command commandDTOToCommand(CommandDTO commandDTO) ;

}
