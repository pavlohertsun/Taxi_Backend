package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Review;

import java.util.ArrayList;
import java.util.List;

public class DriverRatingResponseDto {
    private double rating;
    private int tripsCount;
    private List<ReviewsDto> reviews = new ArrayList<>();

    public DriverRatingResponseDto() {
    }

    public DriverRatingResponseDto(double rating, int tripsCount, List<ReviewsDto> reviews) {
        this.rating = rating;
        this.tripsCount = tripsCount;
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTripsCount() {
        return tripsCount;
    }

    public void setTripsCount(int tripsCount) {
        this.tripsCount = tripsCount;
    }

    public List<ReviewsDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewsDto> reviews) {
        this.reviews = reviews;
    }
}
