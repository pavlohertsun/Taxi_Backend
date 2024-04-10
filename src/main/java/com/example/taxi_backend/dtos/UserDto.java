package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.User;

public class UserDto {
    private String name;
    private User.Role role;

    public UserDto(String name, User.Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
