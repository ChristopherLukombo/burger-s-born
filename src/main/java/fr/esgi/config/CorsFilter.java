package fr.esgi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cors filter allowing cross-domain requests
 * @author christopher
 *
 */
@Component
public class CorsFilter implements Filter {

    private final ConfigurationService configurationService;

    @Autowired
    public CorsFilter(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;

        final String origin = request.getHeader(HttpHeaders.ORIGIN);

        if (configurationService.getCorsAllowedOrigins().contains(origin)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, PATCH, GET, OPTIONS, DELETE");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization,Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma,Location");
        }

        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }

}
