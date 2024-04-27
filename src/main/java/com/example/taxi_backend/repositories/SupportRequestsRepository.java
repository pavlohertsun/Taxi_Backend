package com.example.taxi_backend.repositories;

import com.example.taxi_backend.entities.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRequestsRepository extends JpaRepository<SupportRequest, Long> {
    List<SupportRequest> findAllByStatus(String status);
}
