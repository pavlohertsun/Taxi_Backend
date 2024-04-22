package com.example.taxi_backend.dtos;


public class RegisterCustomerDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private String username;
    private String password;
    private String role;

    public RegisterCustomerDto() {
    }

    public RegisterCustomerDto(String name, String surname, String phoneNumber, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}