package fr.esgi.annotation;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import fr.esgi.config.ErrorMessage;
import fr.esgi.exception.BurgerSTerminalException;

/**
 * Implementation for annotation Authorized.
 * 
 * @author christopher
 *
 */
@Aspect
@Component
public class AuthorizedAspect {
	
	private final MessageSource messageSource;

	@Autowired
	public AuthorizedAspect(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Before("execution(* *.*(..)) && @annotation(authorized)")
	public void checkUser(JoinPoint joinPoint, Authorized authorized) throws BurgerSTerminalException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			final Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
			final Optional<? extends GrantedAuthority> role = authorities.stream().findFirst();

			if (role.isPresent()) {
				boolean found = Stream.of(authorized.values()).anyMatch(r -> r.equals(role.get().toString()));
				if (!found) {
					throw new BurgerSTerminalException(HttpStatus.UNAUTHORIZED.value(),
							messageSource.getMessage(ErrorMessage.ERROR_USER_ROLE_REQUIRED_ACTION, null, Locale.FRENCH));
				}
			}
		}
	}
}