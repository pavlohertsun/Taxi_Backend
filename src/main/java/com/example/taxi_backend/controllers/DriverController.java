package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.DriverDto;
import com.example.taxi_backend.dtos.DriverRatingResponseDto;
import com.example.taxi_backend.dtos.ReviewsDto;
import com.example.taxi_backend.dtos.trip.TripForDriverProfileDto;
import com.example.taxi_backend.entities.*;
import com.example.taxi_backend.repositories.DriverRatingRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import com.example.taxi_backend.repositories.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/driver/")
public class DriverController {
    private DriverRepository driverRepository;
    private DriverRatingRepository driverRatingRepository;
    private ReviewsRepository reviewsRepository;
    @Autowired
    public DriverController(DriverRepository driverRepository, DriverRatingRepository driverRatingRepository, ReviewsRepository reviewsRepository) {
        this.driverRepository = driverRepository;
        this.driverRatingRepository = driverRatingRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable Long id){
        Driver driver = driverRepository.findById(id).get();

        if(driver != null){
            DriverDto driverDto = new DriverDto(driver);
            return ResponseEntity.ok(driverDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/trips")
    public ResponseEntity<Set<TripForDriverProfileDto>> getSetOfTrips(@PathVariable Long id){
        Set<Trip> trips = driverRepository.findById(id).get().getTrips();

        Set<TripForDriverProfileDto> tripResponseDtos = new HashSet<>();

        for(Trip trip : trips){
            tripResponseDtos.add(new TripForDriverProfileDto(trip));
        }

        return ResponseEntity.ok(tripResponseDtos);
    }
    @GetMapping("/{id}/rating")
    public ResponseEntity<DriverRatingResponseDto> getDriverRating(@PathVariable Long id){
        DriverRating driverRating = driverRatingRepository.findById(id).get();

        List<ReviewsDto> reviewsDtos = new ArrayList<>();

        for (Review review: driverRating.getReviews()){
            reviewsDtos.add(new ReviewsDto(review.getComment()));
        }

        return ResponseEntity.ok(new DriverRatingResponseDto(driverRating.getRating(), driverRating.getTripsCount(), reviewsDtos));
    }
}
