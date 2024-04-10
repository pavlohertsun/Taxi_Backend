package com.example.taxi_backend.dtos;

public class AuthResponseDto {
    private JwtToken jwtToken;
    private UserDto user;

    public AuthResponseDto(JwtToken jwtToken, UserDto user) {
        this.jwtToken = jwtToken;
        this.user = user;
    }
}
