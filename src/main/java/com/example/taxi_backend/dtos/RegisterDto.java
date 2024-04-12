package com.example.taxi_backend.dtos;


public class RegisterDto {
    private String username;
    private String password;
    private String role;

    public RegisterDto(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}