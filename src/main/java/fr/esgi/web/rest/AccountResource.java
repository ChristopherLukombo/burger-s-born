package fr.esgi.web.rest;

import static fr.esgi.config.Utils.getLang;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.esgi.config.Constants;
import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;
import fr.esgi.service.UserService;
import fr.esgi.service.dto.UserDTO;
import fr.esgi.web.ManagedUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing the current user's account.
 * @author christopher
 */
@Api(value = "Account")
@RestController
@RequestMapping("/api")
public class AccountResource {

	private static final String FR = "fr";

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
     * @param lang 
     * @return ResponseEntity
     * @throws BurgerSTerminalException 
     * @throws URISyntaxException 
     */
    @ApiOperation(value = "Register the user.")
    @PostMapping("/register")
    public ResponseEntity<Object> registerAccount(
            @RequestBody @Valid ManagedUser managedUser,
            @RequestParam(required = false, defaultValue = FR) String lang
    ) throws BurgerSTerminalException, URISyntaxException {
        if (null != managedUser.getId()) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID, null, getLang(lang)));
        }

        if (!checkPasswordLength(managedUser.getPassword())) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.PASSWORD_IS_NOT_VALID, null, getLang(lang)));
        }

        if (userService.findUserByPseudo(managedUser).isPresent()) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.PSEUDO_IS_ALREADY_REGISTERED, null, getLang(lang)));
        }

        if (userService.findUserByEmail(managedUser).isPresent()) {
            throw new BurgerSTerminalException(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(ErrorMessage.EMAIL_IS_ALREADY_USED, null, getLang(lang)));
        }

        UserDTO userDTO = userService.registerUser(managedUser, managedUser.getPassword());

        return ResponseEntity.created(new URI("/api/users/" + userDTO.getId()))
                .build();
    }
   
    /**
     * POST  /register/file/{userId} : update file from userId
     * 
     * @param file
     * @param userId
     * @return String the status
     * @throws BurgerSTerminalException
     */
    @ApiOperation(value = "Update file from userId.")
    @PostMapping("/register/file/{userId}")
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file,
            @PathVariable("userId") Long userId) throws BurgerSTerminalException {
        LOGGER.info("Call API service store ...");
        try {
            this.userService.store(file, userId);
        } catch (Exception e) {
            throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    messageSource.getMessage(ErrorMessage.ERROR_FAIL_TO_UPLOAD, null, getLang(FR)) +
                            file.getOriginalFilename(), e);
        }
        return ResponseEntity.ok()
                .body("Successfully uploaded : " + file.getOriginalFilename());
    }

    /**
     * GET  /users/imageURL/{pseudo} : retrieve image from pseudo.
     * 
     * @param response
     * @param pseudo
     * @return byte[]
     * @throws BurgerSTerminalException
     */
    @ApiOperation(value = "Retrieve image from pseudo.")
    @GetMapping(value = "/users/imageURL/{pseudo}",
    		produces = {
    				"image/jpg", "image/gif", "image/png", "image/tif"
    })
    public ResponseEntity<byte[]> getImageURL(
    		final HttpServletResponse response,
    		@PathVariable String pseudo) throws BurgerSTerminalException {
    	LOGGER.info("Call API service getImageURL ...");
    	final String contentType;
    	byte[] imageURL;
    	
    	try {
    		final Map.Entry<String, byte[]> entry = userService.getImageURL(pseudo).entrySet().iterator().next();
    		contentType = entry.getKey();
    		imageURL = entry.getValue();
    	} catch (BurgerSTerminalException e) {
    		throw new BurgerSTerminalException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				"Erreur durant la lecture de l'image", e);
    	}

    	response.setHeader("Content-Disposition", "attachment; filename=" + "file." + contentType.split(Constants.DELIMITER)[1]);
    	response.setContentType(contentType);

    	return new ResponseEntity<>(imageURL, HttpStatus.OK);
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @ApiOperation(value = "Check if the user is authenticated, and return its login.")
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
