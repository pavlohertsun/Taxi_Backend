package com.example.taxi_backend.security;

import com.example.taxi_backend.dtos.auth_responce.JwtToken;
import com.example.taxi_backend.dtos.LoginDto;
import com.example.taxi_backend.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private Duration jwtExpiration;
    private final UserService userService;

    private final String USERNAME_CLAIMS_FIELD = "username";
    private final String ROLE_CLAIMS_FIELD = "role";

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public Optional<JwtToken> createToken(LoginDto loginUser) {
        UserDetails userDetails = userService.loadUserByUsername(loginUser.getUsername());

        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME_CLAIMS_FIELD, userDetails.getUsername());
        claims.put(ROLE_CLAIMS_FIELD, userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date currentDate = new Date();

        String token = Jwts.builder()
                .setSubject(loginUser.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + jwtExpiration.toMillis()))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
        return Optional.of(new JwtToken(token));
    }
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Collection<Role> getRoles(String token){
        List<String> roles = (List<String>) getClaims(token).get(ROLE_CLAIMS_FIELD);
        return roles.stream().map(r -> Role.valueOf(r)).toList();
    }
    public String getUsername(String token){
        return (String) getClaims(token).get(USERNAME_CLAIMS_FIELD);
    }
}
