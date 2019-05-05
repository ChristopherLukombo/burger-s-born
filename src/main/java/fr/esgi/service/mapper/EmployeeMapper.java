package fr.esgi.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.esgi.domain.Employee;
import fr.esgi.service.dto.EmployeeDTO;

/**
 * Mapper for the entity Employee and its DTO called EmployeeDTO.
 * @author christopher
 */
@Mapper(uses = { EmployeeDTO.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "hiringDate", target = "hiringDate"),
            @Mapping(source = "user.id", target = "userId"),
    })
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    @InheritInverseConfiguration
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) ;

}
