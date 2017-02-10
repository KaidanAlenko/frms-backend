package hr.eestec_zg.frmsbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class CorsFilter implements Filter {

    private final String frontendUrl;

    public CorsFilter(@Value("${frontend.web.url}") String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", frontendUrl);

        // without this header jquery.ajax calls returns 401 even after successful login and SSESSIONID being succesfully stored.
        res.setHeader("Access-Control-Allow-Credentials", "true");

        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Origin, Content-Type, Version");
        res.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type");

        final HttpServletRequest req = (HttpServletRequest) request;
        if (!Objects.equals(req.getMethod(), "OPTIONS")) {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
