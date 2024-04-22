package com.example.taxi_backend.dtos;

import com.example.taxi_backend.entities.Expense;

import java.sql.Timestamp;

public class ExpenseDto {
    private Timestamp date;
    private double sum;
    private String type;
    private String description;

    public ExpenseDto() {
    }

    public ExpenseDto(Expense expense) {
        this.date = expense.getDate();
        this.sum = expense.getSum();
        this.type = expense.getType();
        this.description = expense.getDescription();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
