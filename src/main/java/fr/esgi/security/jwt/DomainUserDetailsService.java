package fr.esgi.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.esgi.dao.UserRepository;
import fr.esgi.domain.Role;
import fr.esgi.domain.User;
import fr.esgi.enums.RoleName;
import fr.esgi.exception.BurgerSTerminalException;

/**
 * Authenticate a user from the database.
 * @author christopher
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        LOGGER.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userByEmailFromDatabase = userRepository.findOneByEmailIgnoreCase(lowercaseLogin);
        return userByEmailFromDatabase.map(user -> {
            try {
                return createSpringSecurityUser(lowercaseLogin, user);
            } catch (BurgerSTerminalException e) {
                return null;
            }
        }).orElseGet(() -> {
            Optional<User> userByLoginFromDatabase = userRepository.findOneByPseudoIgnoreCase(lowercaseLogin);
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
        if (!user.isActivated()) {
            throw new BurgerSTerminalException("User " + lowercaseLogin + " was not activated");
        }

        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(getRole(user)));

        return new org.springframework.security.core.userdetails.User(user.getPseudo(),
                user.getPassword(),
                grantedAuthorities);
    }

    private String getRole(User user) {
    	return Optional.of(user).map(User::getRole)
    			.map(Role::getName)
    			.orElse(RoleName.ROLE_CUSTOMER.toString());
    }
}
