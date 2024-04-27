package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.IncomeDto;
import com.example.taxi_backend.entities.Expense;
import com.example.taxi_backend.entities.Income;
import com.example.taxi_backend.repositories.ExpenseRepository;
import com.example.taxi_backend.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomesController {
    private IncomeRepository incomeRepository;

    @Autowired
    public IncomesController(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @GetMapping("/{date}")
    public List<IncomeDto> getAllCurrentIncomesTransactionsInMonth(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startOfMonth = LocalDateTime.parse(date, formatter);
        LocalDateTime endOfMonth = startOfMonth.plusDays(1);

        List<Income> incomes = incomeRepository.findAllByDateBetween(startOfMonth, endOfMonth);

        List<IncomeDto> incomeDtos = new ArrayList<>();

        for (Income income : incomes) {
            incomeDtos.add(new IncomeDto(income));
        }

        return incomeDtos;
    }

    @GetMapping("/sum/{date}")
    public Double getSumOfIncomes(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startOfMonth = LocalDateTime.parse(date, formatter);
        LocalDateTime endOfMonth = startOfMonth.plusDays(1);

        List<Income> incomes = incomeRepository.findAllByDateBetween(startOfMonth, endOfMonth);
        double sum = 0;

        for (Income income : incomes) {
            sum += income.getSum();
        }
        return sum;
    }
}
