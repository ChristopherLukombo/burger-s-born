package fr.esgi.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Login object for storing a user's credentials.
 * @author christopher
 */
public class Login {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = ManagedUser.PASSWORD_MIN_LENGTH, max = ManagedUser.PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
