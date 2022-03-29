package com.example.piwater.config;


import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {

    static final String ORIGIN = "Origin";

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String origin = request.getHeader(ORIGIN);

        response.setHeader("access-control-allow-origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type, authorization, access-control-allow-origin");

        if (request.getMethod().equals("OPTIONS"))
            response.setStatus(HttpServletResponse.SC_OK);
        else
            filterChain.doFilter(request, response);

    }

}