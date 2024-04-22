package com.example.taxi_backend.repositories;

import com.example.taxi_backend.entities.TripRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRatingRepository extends JpaRepository<TripRating, Long> {
}
