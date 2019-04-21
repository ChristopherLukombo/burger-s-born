package fr.esgi.web;

import javax.validation.constraints.Size;

import fr.esgi.service.dto.UserDTO;

/**
 * ManagedUser extending the UserDTO.
 * @author christopher
 */
public class ManagedUser extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUser() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
