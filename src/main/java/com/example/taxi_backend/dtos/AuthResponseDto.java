package com.example.taxi_backend.dtos;

public class AuthResponseDto {
    private JwtToken jwtToken;

    private UserDto userDto;

    public AuthResponseDto(JwtToken jwtToken, UserDto user) {
        this.jwtToken = jwtToken;
        this.userDto = user;
    }

    public JwtToken getJwtToken() {
        return jwtToken;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
