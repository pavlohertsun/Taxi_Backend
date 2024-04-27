package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.LogResponseDto;
import com.example.taxi_backend.entities.Log;
import com.example.taxi_backend.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    private LogRepository logRepository;

    @Autowired
    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    @GetMapping("/{date}")
    public List<LogResponseDto> getAllCurrentIncomesTransactionsInMonth(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startOfMonth = LocalDateTime.parse(date, formatter);
        LocalDateTime endOfMonth = startOfMonth.plusDays(1);

        List<Log> logs = logRepository.findAllByDateBetween(startOfMonth, endOfMonth);

        List<LogResponseDto> responseDtos = new ArrayList<>();

        for (Log log : logs) {
            responseDtos.add(new LogResponseDto(log));
        }

        return responseDtos;
    }
}
