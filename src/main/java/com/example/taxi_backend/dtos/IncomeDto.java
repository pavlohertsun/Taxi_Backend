package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Income;

import java.sql.Timestamp;

public class IncomeDto {
    private Timestamp date;
    private double sum;
    private String description;

    public IncomeDto() {
    }

    public IncomeDto(Income income) {
        this.date = income.getDate();
        this.sum = income.getSum();
        this.description = income.getDescription();
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
