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
    
    @Value("${app.imagesDirectory}")
    private String imagesDirectory;
    
    @Value("${app.paypal.clientId}")
    private String paypalClientId;

    @Value("${app.paypal.clientSecret}")
    private String paypalClientSecret;
    
    @Value("${app.paypal.cancelUrl}")
    private String paypalCancelUrl;
    
    @Value("${app.paypal.ReturnUrl}")
    private String paypalReturnUrl;
    
    public Set<String> getCorsAllowedOrigins() {
        return corsAllowedOrigins;
    }

	public String getImagesDirectory() {
		return imagesDirectory;
	}

	public String getPaypalClientId() {
		return paypalClientId;
	}

	public String getPaypalClientSecret() {
		return paypalClientSecret;
	}

	public String getPaypalCancelUrl() {
		return paypalCancelUrl;
	}

	public String getPaypalReturnUrl() {
		return paypalReturnUrl;
	}
	
}
