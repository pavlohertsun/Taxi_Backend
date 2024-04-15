package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.CustomerDto;
import com.example.taxi_backend.dtos.DriverDto;
import com.example.taxi_backend.dtos.trip.TripForDriverProfileDto;
import com.example.taxi_backend.dtos.trip.TripResponseDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/driver/")
public class DriverController {
    private DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
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
}
