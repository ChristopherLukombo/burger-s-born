package fr.esgi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * PropertiesConfig for import configuration file.
 * @author christopher
 */
@Configuration
@ConfigurationProperties
@PropertySources(value = {@PropertySource(value = "file:${CONF_DIR}/burgersterminal.properties", ignoreResourceNotFound = true)})
public class PropertiesConfig {

    //To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setOrder(1);
		return configurer;
	}

}
