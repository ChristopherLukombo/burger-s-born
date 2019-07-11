package fr.esgi.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.esgi.security.jwt.JWTConfigurer;
import fr.esgi.security.jwt.TokenProvider;
import fr.esgi.service.CustomerService;
import fr.esgi.service.ManagerService;
import fr.esgi.web.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * UserJWTController to authenticate users.
 * @author christopher
 */
@Api(value = "UserJWT")
@RestController
@RequestMapping("/api")
public class UserJWTResource {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;
    
    private final CustomerService customerService;
    
    private final ManagerService managerService;
    
    @Autowired
	public UserJWTResource(TokenProvider tokenProvider, AuthenticationManager authenticationManager,
			CustomerService customerService, ManagerService managerService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
		this.customerService = customerService;
		this.managerService = managerService;
	}

	/**
     * Authenticate the user and return the token which identify him.
     * @param login
     * @return JWTToken
     */
    @ApiOperation(value = "Authenticate the user and return the token which identify him.")
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody Login login) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
 
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (login.isRememberMe() == null) ? false : login.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        final Long idCustomer = customerService.findByUserName(login.getUsername());
        final Long idManager = managerService.findByUserName(login.getUsername());
		return new ResponseEntity<>(new JWTToken(jwt, idCustomer, idManager), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private Long idCustomer;
        private Long idManager;

		public JWTToken(String idToken, Long idCustomer, Long idManager) {
			this.idToken = idToken;
			this.idCustomer = idCustomer;
			this.idManager = idManager;
		}

		@JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_customer")
        Long getIdCustomer() {
			return idCustomer;
		}

	    void setIdCustomer(Long idCustomer) {
			this.idCustomer = idCustomer;
		}

	    @JsonProperty("id_manager")
		Long getIdManager() {
			return idManager;
		}

		void setIdManager(Long idManager) {
			this.idManager = idManager;
		}
    }
}
