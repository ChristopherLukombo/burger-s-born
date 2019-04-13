package fr.esgi.service.mapper;

import fr.esgi.domain.User;
import fr.esgi.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * @author christopher
 */
public abstract class UserMapperDecorator implements UserMapper {

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setPseudo(userDTO.getPseudo());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            return user;
        }
    }

}

