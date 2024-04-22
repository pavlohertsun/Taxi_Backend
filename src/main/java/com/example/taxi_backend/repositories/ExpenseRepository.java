package com.example.taxi_backend.repositories;

import com.example.taxi_backend.entities.Expense;
import com.example.taxi_backend.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
