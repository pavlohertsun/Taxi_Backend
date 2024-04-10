package com.example.taxi_backend.dtos.trip;

public class TripResponseDto {
    private double price;

    public TripResponseDto(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
