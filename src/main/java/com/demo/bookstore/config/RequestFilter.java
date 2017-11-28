package com.demo.bookstore.config;

/*
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, X-Auth-Token");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");


        if(!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            try {
                filterChain.doFilter(servletRequest,servletResponse);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Pre-fight");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Auth-Token, " +
                    "Access-Control-Request-Headers,Access-Control-Request-Method,Accept,Origin,Authorization,X-Requested-With");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    public void destroy() {

    }
}
*/
