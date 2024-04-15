package com.example.taxi_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String surname;

    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private boolean license;
    private double balance;
    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private Set<Trip> trips = new HashSet<>();

    public Driver() {
    }

    public Driver(String name, String surname, String email, String phoneNumber, boolean license, double balance) {
        this.name = name;
        this.surname = surname;
        this.email  = email;
        this.phoneNumber = phoneNumber;
        this.license = license;
        this.balance = balance;
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

    public boolean getLicense() {
        return license;
    }

    public void setLicense(boolean license) {
        this.license = license;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", license='" + license + '\'' +
                ", balance=" + balance +
                ", trips=" + trips +
                '}';
    }
}
