package com.example.taxi_backend.dtos;

public class SupportResponseDto {
    private long id;
    private String response;
    private String status;

    public SupportResponseDto() {
    }

    public SupportResponseDto(long id, String response, String status) {
        this.id = id;
        this.response = response;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
