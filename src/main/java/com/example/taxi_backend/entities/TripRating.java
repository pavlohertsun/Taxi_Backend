package com.example.taxi_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trips_rating")
public class TripRating {
    @Id
    private long id;
    private String comment;
    private int cRate;
    private int dRate;

    public TripRating() {

    }

    public TripRating(long id, String comment, int cRate, int dRate) {
        this.id = id;
        this.comment = comment;
        this.cRate = cRate;
        this.dRate = dRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getcRate() {
        return cRate;
    }

    public void setcRate(int cRate) {
        this.cRate = cRate;
    }

    public int getdRate() {
        return dRate;
    }

    public void setdRate(int dRate) {
        this.dRate = dRate;
    }
}
