package org.benevity.server.config.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,DELETE");
        response.setHeader("Access-Control-Allow-Headers",
                           "Authorization,content-type,access-control-request-headers,access-control-request-method,accept,Origin,x-requested-with");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        String requestMethod = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
            log.warn("Pre-flight");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                chain.doFilter(req, res);
            } catch (Exception e) {
                log.error("Error filtering: ", e);
            }
        }
    }
}
