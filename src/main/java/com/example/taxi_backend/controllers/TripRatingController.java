package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.TripRatingDto;
import com.example.taxi_backend.entities.TripRating;
import com.example.taxi_backend.repositories.TripRatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class TripRatingController {

    private TripRatingRepository tripRatingRepository;
    @Autowired
    public TripRatingController(TripRatingRepository tripRatingRepository) {
        this.tripRatingRepository = tripRatingRepository;
    }

    @PostMapping("")
    public void leaveReview(@RequestBody TripRatingDto tripRatingDto) {
        Optional<TripRating> existingRatingOptional = tripRatingRepository.findById(tripRatingDto.getId());

        if (existingRatingOptional.isPresent()) {
            TripRating existingRating = existingRatingOptional.get();
            if (tripRatingDto.getcRate() != 0) {
                existingRating.setcRate(tripRatingDto.getcRate());
            }
            if (tripRatingDto.getdRate() != 0) {
                existingRating.setdRate(tripRatingDto.getdRate());
            }
            if (tripRatingDto.getComment() != null) {
                existingRating.setComment(tripRatingDto.getComment());
            }
            tripRatingRepository.save(existingRating);
        } else {
            TripRating tripRating = new TripRating();
            tripRating.setId(tripRatingDto.getId());
            tripRating.setcRate(tripRatingDto.getcRate());
            tripRating.setdRate(tripRatingDto.getdRate());
            tripRating.setComment(tripRatingDto.getComment());
            tripRatingRepository.save(tripRating);
        }
    }
}
