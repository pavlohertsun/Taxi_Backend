package com.example.taxi_backend.dtos.user;

import com.example.taxi_backend.entities.Role;

public class UserDto {
    private long id;
    private String name;
    private Role role;


    public UserDto(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
