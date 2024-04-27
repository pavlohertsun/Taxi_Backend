package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Driver;

public class DriverDto {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private double balance;
    private boolean license;
    private String status;
    public DriverDto(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.surname = driver.getSurname();
        this.email = driver.getEmail();
        this.phoneNumber = driver.getPhoneNumber();
        this.balance = driver.getBalance();
        this.license = driver.getLicense();
        this.status = driver.getStatus();
    }

    public DriverDto() {
    }

    public DriverDto(long id, String name, String surname, String email, String phoneNumber, double balance, boolean license) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.license = license;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isLicense() {
        return license;
    }

    public void setLicense(boolean license) {
        this.license = license;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
