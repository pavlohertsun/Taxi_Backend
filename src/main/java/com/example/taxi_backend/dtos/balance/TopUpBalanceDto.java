package com.example.taxi_backend.dtos.balance;

public class TopUpBalanceDto {
    private long userId;
    private double sum;

    public TopUpBalanceDto() {
    }

    public TopUpBalanceDto(long userId, double sum) {
        this.userId = userId;
        this.sum = sum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
