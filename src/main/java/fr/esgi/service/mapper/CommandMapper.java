package fr.esgi.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.esgi.domain.Command;
import fr.esgi.service.dto.CommandDTO;

/**
 * Mapper for the entity Command and its DTO called CommandDTO.
 * @author christopher
 */
@Mapper(uses = { CommandDTO.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(CommandMapperDecorator.class)
public interface CommandMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "orderStatus", target = "orderStatus"),
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "customer.id", target = "customerId"),
            @Mapping(source = "paymentId", target = "paymentId"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "saleId", target = "saleId"),
    })
    CommandDTO commandToCommandDTO(Command command);

    @InheritInverseConfiguration
    Command commandDTOToCommand(CommandDTO commandDTO) ;

}
