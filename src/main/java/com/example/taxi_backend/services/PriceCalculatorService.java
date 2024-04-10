package com.example.taxi_backend.services;

import org.springframework.stereotype.Service;

@Service
public class PriceCalculatorService {
    private double pricePerMeter = 0.02;
    public double calculatePrice(double length, String rate){
        return length * pricePerMeter * convertRateToCoefficient(rate);
    }
    private double convertRateToCoefficient(String rate){
        if(rate.equals("Low")) return 1.0;
        else if (rate.equals("Normal")) return 1.5;
        else return 2.0;
    }
}
