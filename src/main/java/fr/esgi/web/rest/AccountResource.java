package fr.esgi.web.rest;

import fr.esgi.dao.UserRepository;
import fr.esgi.domain.User;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.UserService;
import fr.esgi.web.ManagedUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * REST controller for managing the current user's account.
 * @author christopher
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    public AccountResource(UserRepository userRepository, UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUser the managed user View Model
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@RequestBody ManagedUser managedUser) throws BurgerSTerminalException {
        if (!checkPasswordLength(managedUser.getPassword())) {
            throw new BurgerSTerminalException("Password is not valid");
        }
        userRepository.findOneByLogin(managedUser.getLogin().toLowerCase()).ifPresent(u -> {
            try {
                throw new BurgerSTerminalException("Erreur");
            } catch (BurgerSTerminalException e) {
                return;
            }
        });
        userRepository.findOneByEmailIgnoreCase(managedUser.getEmail()).ifPresent(u -> {
            try {
                throw new BurgerSTerminalException("Erreur");
            } catch (BurgerSTerminalException e) {
                return;
            }
        });
        // TODO: retourner une ReponseEntity avec une URI.
        User user = userService.registerUser(managedUser, managedUser.getPassword());
    }


    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public ResponseEntity<String> isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return new ResponseEntity<>(request.getRemoteUser(), HttpStatus.OK);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUser.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUser.PASSWORD_MAX_LENGTH;
    }
}
