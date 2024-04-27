package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.ExpenseDto;
import com.example.taxi_backend.dtos.ExpenseRequestDto;
import com.example.taxi_backend.dtos.IncomeDto;
import com.example.taxi_backend.entities.Expense;
import com.example.taxi_backend.entities.Income;
import com.example.taxi_backend.repositories.ExpenseRepository;
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
@RequestMapping("/api/expenses")
public class ExpensesController {
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpensesController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @GetMapping("/{date}")
    public List<ExpenseDto> getAllExpensesTransactionsInDay(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startOfMonth = LocalDateTime.parse(date, formatter);
        LocalDateTime endOfMonth = startOfMonth.plusDays(1);

        List<Expense> expenses = expenseRepository.findAllByDateBetween(startOfMonth, endOfMonth);

        List<ExpenseDto> expenseDtos = new ArrayList<>();

        for (Expense expense : expenses) {
            expenseDtos.add(new ExpenseDto(expense));
        }

        return expenseDtos;
    }

    @GetMapping("/sum/{date}")
    public Double getSumOfExpenses(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startOfMonth = LocalDateTime.parse(date, formatter);
        LocalDateTime endOfMonth = startOfMonth.plusDays(1);

        List<Expense> expenses = expenseRepository.findAllByDateBetween(startOfMonth, endOfMonth);
        double sum = 0;

        for (Expense expense : expenses) {
            sum += expense.getSum();
        }
        return sum;
    }

    @PostMapping("")
    public void createExpense(@RequestBody ExpenseRequestDto requestDto){
        Expense expense = new Expense();

        expense.setSum(requestDto.getSum());
        expense.setType(requestDto.getType());
        expense.setDescription(requestDto.getDescription());

        expenseRepository.save(expense);
    }
}
