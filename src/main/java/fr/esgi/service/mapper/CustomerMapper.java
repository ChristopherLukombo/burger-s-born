package fr.esgi.service.mapper;

import fr.esgi.domain.Customer;
import fr.esgi.service.dto.CustomerDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Customer and its DTO called CustomerDTO.
 * @author christopher
 */
@Mapper(uses = { CustomerDTO.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "zipCode", target = "zipCode"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "user.id", target = "userId"),
    })
    CustomerDTO customerToCustomerDTO(Customer customer);

    @InheritInverseConfiguration
    Customer customerDTOToCustomer(CustomerDTO customerDTO) ;

}
