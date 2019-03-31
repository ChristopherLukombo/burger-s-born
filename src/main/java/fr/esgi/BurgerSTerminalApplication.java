package fr.esgi;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import fr.esgi.config.PropertiesConfig;
@EnableConfigurationProperties({PropertiesConfig.class})
@SpringBootApplication
public class BurgerSTerminalApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BurgerSTerminalApplication.class);

	public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(BurgerSTerminalApplication.class);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        LOGGER.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\t" +
                "\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"));
	}

}
