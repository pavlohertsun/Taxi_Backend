package com.example.taxi_backend.dtos.price;

import com.google.maps.model.LatLng;

public class PriceRequestDto {
    private LatLng startAddress;
    private LatLng endAddress;
    private String rate;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public LatLng getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(LatLng startAddress) {
        this.startAddress = startAddress;
    }

    public LatLng getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(LatLng endAddress) {
        this.endAddress = endAddress;
    }
}
