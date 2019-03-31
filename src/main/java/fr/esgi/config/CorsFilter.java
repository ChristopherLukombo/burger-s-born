package fr.esgi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Cors filter allowing cross-domain requests
 * @author christopher
 *
 */
@Component
public class CorsFilter implements Filter {

    @Value("#{'${app.authorizedURLs}'.split(',')}")
    private Collection<String> authorizedURLs;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
       HttpServletResponse response = (HttpServletResponse) res;

       response.setHeader("Access-Control-Allow-Origin", authorizedURLs.toString());
       response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
       response.setHeader("Access-Control-Max-Age", "3600");
       response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
       chain.doFilter(req, res);
   }
}
