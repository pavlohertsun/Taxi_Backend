package com.example.taxi_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtGenerator {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.secret}")
    private Duration jwtExpiration;

    private UserService userService;
    @Autowired
    public JwtGenerator(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + jwtExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();

        return token;
    }
    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT expired or incorrect");
        }
    }
}
