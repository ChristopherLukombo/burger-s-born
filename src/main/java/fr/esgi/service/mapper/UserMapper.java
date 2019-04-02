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
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "activated", target = "activated"),
            @Mapping(source = "langKey", target = "langKey"),
    })
    UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    User userDTOToUser(UserDTO userDTO) ;

}
