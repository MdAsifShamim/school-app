package com.school.app.filter;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGenerator extends OncePerRequestFilter {

    @Value("${schoolApp.salt}")
    private String saltKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){

            Environment env=getEnvironment();
            if(env!=null){
                String secrete= env.getProperty("fksxcvrtypwe53S22S0gererekSqwSg","fksxcvrtypwe53S22S0gkSqwSg");
                SecretKey secretKey= Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_16));
                String jwtToken =Jwts.builder().issuer("School App")
                        .subject("JWT Token Generate")
                        .claim("username",authentication.getName())
                        .claim("authorities",authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime()+30000000))
                        .signWith(secretKey)
                        .compact();
                response.setHeader("Authorization",jwtToken);
            }
        }

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().endsWith("/user");
    }
}
