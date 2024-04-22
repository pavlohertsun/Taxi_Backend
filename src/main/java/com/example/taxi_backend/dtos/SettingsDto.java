package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Settings;

public class SettingsDto {
    private String rate;
    private int percent;

    public SettingsDto() {
    }

    public SettingsDto(Settings setting) {
        this.rate = setting.getRate();
        this.percent = setting.getPercent();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
