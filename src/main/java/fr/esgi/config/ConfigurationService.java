package fr.esgi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * ConfigurationService for managing configuration properties.
 * @author christopher
 */
@Configuration
public class ConfigurationService {

    @Value("#{'${app.authorizedURLs}'.split(',')}")
    private Set<String> corsAllowedOrigins;

    public Set<String> getCorsAllowedOrigins() {
        return corsAllowedOrigins;
    }

}
