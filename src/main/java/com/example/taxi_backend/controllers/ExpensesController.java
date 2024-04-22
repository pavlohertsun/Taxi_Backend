package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.ExpenseDto;
import com.example.taxi_backend.dtos.IncomeDto;
import com.example.taxi_backend.entities.Expense;
import com.example.taxi_backend.entities.Income;
import com.example.taxi_backend.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpensesController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @GetMapping("/{month}")
    public List<ExpenseDto> getAllMonthExpensesTransactions(@PathVariable Integer month){
        LocalDateTime startOfMonth = LocalDateTime.of(LocalDate.now().getYear(), month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        List<Expense> expenses = expenseRepository.findAllByDateBetween(startOfMonth, endOfMonth);

        List<ExpenseDto> expenseDtos = new ArrayList<>();

        for (Expense expense : expenses){
            expenseDtos.add(new ExpenseDto(expense));
        }

        return expenseDtos;
    }
    @GetMapping("/sum/{month}")
    public Double getSumOfExpenses(@PathVariable Integer month){
        LocalDateTime startOfMonth = LocalDateTime.of(LocalDate.now().getYear(), month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        List<Expense> expenses = expenseRepository.findAllByDateBetween(startOfMonth, endOfMonth);
        double sum = 0;

        for (Expense expense : expenses){
           sum += expense.getSum();
        }
        return sum;
    }
}
