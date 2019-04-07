package fr.esgi.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import fr.esgi.exception.BurgerSTerminalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.UserRepository;
import fr.esgi.domain.User;

/**
 * Authenticate a user from the database.
 * @author christopher
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        LOGGER.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userByEmailFromDatabase = userRepository.findOneWithAuthoritiesByEmail(lowercaseLogin);
        return userByEmailFromDatabase.map(user -> {
            try {
                return createSpringSecurityUser(lowercaseLogin, user);
            } catch (BurgerSTerminalException e) {
                return null;
            }
        }).orElseGet(() -> {
            Optional<User> userByLoginFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
            return userByLoginFromDatabase.map(user -> {
                try {
                    return createSpringSecurityUser(lowercaseLogin, user);
                } catch (BurgerSTerminalException e) {
                    return null;
                }
            })
                    .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
                            "database"));
        });
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) throws BurgerSTerminalException {
        if (!user.getActivated()) {
            throw new BurgerSTerminalException("User " + lowercaseLogin + " was not activated");
        }

        // TODO: Gérer les rôles
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                grantedAuthorities);
    }
}
