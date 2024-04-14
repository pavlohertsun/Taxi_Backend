package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Trip;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class CustomerDto {
    private long id;

    private String name;
    private String surname;

    private String email;
    private String phoneNumber;
    private double rating;
    private Set<Trip> trips = new HashSet<>();

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.rating = customer.getRating();
        this.trips = customer.getTrips();
    }

    public CustomerDto() {
    }

    public CustomerDto(long id, String name, String surname, String email, String phoneNumber, double rating, Set<Trip> trips) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.trips = trips;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }
}
