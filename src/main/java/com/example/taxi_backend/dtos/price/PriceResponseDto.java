package com.example.taxi_backend.dtos.price;

public class PriceResponseDto {
    private double price;

    public PriceResponseDto(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
