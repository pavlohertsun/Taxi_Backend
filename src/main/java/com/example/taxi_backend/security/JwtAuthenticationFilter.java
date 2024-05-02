package com.example.taxi_backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(header!=null && header.startsWith("Bearer ")){
            token = header.substring(7);
            try {
                username = jwtService.getUsername(token);
            }
            catch (ExpiredJwtException e){
                logger.debug("Token expired!");
            }
            catch (SignatureException e){
                System.out.println(e);
                logger.debug("SignatureException!");
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var roles = jwtService.getRoles(token)
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roles
                    );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
