package com.example.taxi_backend.dtos.trip;

public class DriverResponseDto {
    private String name;
    private String surname;
    private boolean arrived;
    private boolean ended;


    public DriverResponseDto() {
    }

    public DriverResponseDto(String name, String surname, boolean arrived, boolean ended) {
        this.name = name;
        this.surname = surname;
        this.arrived = arrived;
        this.ended = ended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
