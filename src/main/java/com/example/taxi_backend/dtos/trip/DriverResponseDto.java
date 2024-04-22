package com.example.taxi_backend.dtos.trip;

public class DriverResponseDto {
    private long id;
    private String name;
    private String surname;
    private String licensePlate;
    private boolean arrived;
    private boolean ended;


    public DriverResponseDto() {
    }

    public DriverResponseDto(long id, String name, String surname, String licensePlate, boolean arrived, boolean ended) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.licensePlate = licensePlate;
        this.arrived = arrived;
        this.ended = ended;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
