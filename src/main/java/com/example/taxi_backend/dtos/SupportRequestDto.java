package com.example.taxi_backend.dtos;

public class SupportRequestDto {
    private String request;
    private long customerId;
    private long driverId;

    public SupportRequestDto() {
    }
    public SupportRequestDto(String request, long customer_id, long driverId) {
        this.request = request;
        this.customerId = customer_id;
        this.driverId = driverId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }
}
