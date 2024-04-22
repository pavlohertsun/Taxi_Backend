package com.example.taxi_backend.dtos;

public class ReviewsDto {
    private String comment;

    public ReviewsDto() {
    }

    public ReviewsDto(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
