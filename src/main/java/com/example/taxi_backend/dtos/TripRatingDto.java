package com.example.taxi_backend.dtos;

public class TripRatingDto {
    private long id;
    private String comment;
    private int cRate;
    private int dRate;

    public TripRatingDto() {
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
