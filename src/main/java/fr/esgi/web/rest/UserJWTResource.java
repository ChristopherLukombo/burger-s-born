package fr.esgi.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esgi.security.jwt.JWTConfigurer;
import fr.esgi.security.jwt.TokenProvider;
import fr.esgi.web.Login;
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

import javax.validation.Valid;

/**
 * UserJWTController to authenticate users.
 * @author christopher
 */
@RestController
@RequestMapping("/api")
public class UserJWTResource {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserJWTResource(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticate the user and return the token which identify him.
     * @param login
     * @return JWTToken
     */
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
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
