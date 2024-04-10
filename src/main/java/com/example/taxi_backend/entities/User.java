package com.example.taxi_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    //    private String surname;
//    private String email;
//
//    @Column(name = "phone_number")
//    private String phoneNumber;
//    private String password;
//    private Role role;
//    private int rating;

//    @OneToMany(mappedBy = "user")
//    private Set<Trip> trips = new HashSet<>();



    public enum Role{
        USER,
        ADMIN
    }
}