package com.example.taxi_backend.repositories;

import com.example.taxi_backend.entities.DriverRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {
}
