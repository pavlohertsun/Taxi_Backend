package com.example.taxi_backend.dtos.trip;

import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Trip;

import java.sql.Timestamp;

public class TripResponseDto {
    private Timestamp startTime;
    private Timestamp endTime;
    private String startPoint;
    private String endPoint;
    private double price;
    private String status;
    private String rate;
    private String description;
    private String driverName;
    private String driverSurname;

    public TripResponseDto(Trip trip) {
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPoint = trip.getStartPoint();
        this.endPoint = trip.getEndPoint();
        this.price = trip.getPrice();
        this.status = trip.getStatus();
        this.rate = trip.getRate();
        this.description = trip.getDescription();
        if(trip.getDriver() == null){
            Driver driver = new Driver();
            driver.setName("");
            driver.setSurname("");

            this.driverName = driver.getName();
            this.driverSurname = driver.getSurname();
        }
        else {
            this.driverName = trip.getDriver().getName();
            this.driverSurname = trip.getDriver().getSurname();
        }
    }

    public TripResponseDto() {
    }

    public TripResponseDto(Timestamp startTime, Timestamp endTime, String startPoint, String endPoint, double price, String status, String rate, String description, String driverName, String driverSurname) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.price = price;
        this.status = status;
        this.rate = rate;
        this.description = description;
        this.driverName = driverName;
        this.driverSurname = driverSurname;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }
}
