package fr.esgi.web.rest;

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.UserService;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.web.ManagedUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static fr.esgi.config.Utils.getLang;

/**
 * REST controller for managing the current user's account.
 * @author christopher
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    private final MessageSource messageSource;

    @Autowired
    public AccountResource(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUser the managed user View Model
     */
    @PostMapping("/register")
    public ResponseEntity registerAccount(
            @RequestBody @Valid ManagedUser managedUser,
            @RequestParam(required = false, defaultValue = "fr") String lang
    ) throws BurgerSTerminalException, URISyntaxException {
        if (!checkPasswordLength(managedUser.getPassword())) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.PASSWORD_IS_NOT_VALID, null, getLang(lang)));
        }

        if (userService.loginIsPresent(managedUser)) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.PSEUDO_IS_ALREADY_REGISTERED, null, getLang(lang)));
        }

        if (userService.emailIsPresent(managedUser)) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.EMAIL_IS_ALREADY_USED, null, getLang(lang)));
        }

        UserDTO userDTO = userService.registerUser(managedUser, managedUser.getPassword());

        return ResponseEntity.created(new URI("/api/users/" + userDTO.getId()))
                .build();
    }

    @PostMapping("/register/file/{userId}")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("userId") Long userId) throws BurgerSTerminalException {
        LOGGER.info("Call API service store ...");

        try {
            this.userService.store(file, userId);
        } catch (Exception e) {
            throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    messageSource.getMessage(ErrorMessage.ERROR_FAIL_TO_UPLOAD, null, getLang("fr")) + file.getOriginalFilename(), e);
        }

        return ResponseEntity.ok().body("Successfully uploaded : " + file.getOriginalFilename());
    }


    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public ResponseEntity<String> isAuthenticated(HttpServletRequest request) {
        LOGGER.debug("REST request to check if the current user is authenticated");
        return new ResponseEntity<>(request.getRemoteUser(), HttpStatus.OK);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= ManagedUser.PASSWORD_MIN_LENGTH &&
                password.length() <= ManagedUser.PASSWORD_MAX_LENGTH;
    }

}
