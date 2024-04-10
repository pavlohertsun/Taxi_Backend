package com.example.taxi_backend.dtos.trip;

import java.time.LocalDateTime;

public class TripRequestDto {
    private LocalDateTime startTime;
    private String startPoint;
    private String endPoint;
    private double price;
    private String status;
    private String rate;
    private String description;
    private long userId;
    private long driverId;
}
