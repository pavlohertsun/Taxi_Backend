package com.example.taxi_backend.repositories;

import com.example.taxi_backend.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
