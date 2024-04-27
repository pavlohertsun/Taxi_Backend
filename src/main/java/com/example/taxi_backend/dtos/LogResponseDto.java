package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Log;

import java.sql.Timestamp;

public class LogResponseDto {
    private String message;
    private Timestamp date;

    public LogResponseDto() {
    }

    public LogResponseDto(Log log) {
        this.message = log.getMessage();
        this.date = log.getDate();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
