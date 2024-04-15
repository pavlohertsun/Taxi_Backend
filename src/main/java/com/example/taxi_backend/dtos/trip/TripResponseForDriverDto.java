package com.example.taxi_backend.dtos.trip;

import com.example.taxi_backend.entities.Trip;

public class TripResponseForDriverDto {
    private String startPoint;
    private String endPoint;
    private double price;
    private String description;
    private String userName;
    private String userPhone;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public TripResponseForDriverDto(Trip trip) {
        this.startPoint = trip.getStartPoint();
        this.endPoint = trip.getEndPoint();
        this.price = trip.getPrice();
        this.description = trip.getDescription();
        this.userName = trip.getUser().getName();
        this.userPhone = trip.getUser().getPhoneNumber();
    }

    public TripResponseForDriverDto() {
    }

    public TripResponseForDriverDto(String startPoint, String endPoint, double price, String description, String userName) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.price = price;
        this.description = description;
        this.userName = userName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
