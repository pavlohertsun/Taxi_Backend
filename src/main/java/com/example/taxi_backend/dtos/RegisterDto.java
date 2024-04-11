package com.example.taxi_backend.dtos;


public class RegisterDto {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String phoneNumber;
    private String role;
    private int rating;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public int getRating() {
        return rating;
    }
}