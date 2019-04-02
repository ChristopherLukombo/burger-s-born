package fr.esgi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @Autowired
    private ConfigurationService configurationService;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader(HttpHeaders.ORIGIN);

        if (configurationService.getCorsAllowedOrigins().contains(origin)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, PATCH, GET, OPTIONS, DELETE");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
            chain.doFilter(req, res);
        }

    }

}
