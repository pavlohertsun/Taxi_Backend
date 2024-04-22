package com.example.taxi_backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Timestamp date;
    private double sum;
    private String type;
    private String description;

    public Expense() {
    }

    public Expense(long id, Timestamp date, double sum, String type, String description) {
        this.id = id;
        this.date = date;
        this.sum = sum;
        this.type = type;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
