package com.example.taxi_backend.dtos;

public class JwtToken {
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
