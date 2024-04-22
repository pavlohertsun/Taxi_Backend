package com.example.taxi_backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers_rating")
public class DriverRating {
    @Id
    private long id;
    private double rating;
    @Column(name = "trips_count")
    private int tripsCount;

    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();

    public DriverRating() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
