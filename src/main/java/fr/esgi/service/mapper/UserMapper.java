package fr.esgi.service.mapper;

import fr.esgi.domain.User;
import fr.esgi.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * @author christopher
 */
@Mapper(uses = { UserDTO.class }, componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "pseudo", target = "pseudo"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "activated", target = "activated"),
            @Mapping(source = "birthDay", target = "birthDay"),
            @Mapping(source = "role.id", target = "roleId"),
    })
    UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    User userDTOToUser(UserDTO userDTO) ;

}
