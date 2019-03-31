package fr.esgi.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import fr.esgi.domain.User;
import fr.esgi.service.dto.UserDTO;

@Mapper(uses = { UserDTO.class }, componentModel = "spring")
public interface UserMapper {


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "firstName", target = "firstName")
    })
    User toEntity(UserDTO userDTO);

    @InheritInverseConfiguration
    UserDTO toDto(User user);

}
