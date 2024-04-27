package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.price.PriceRequestDto;
import com.example.taxi_backend.dtos.price.PriceResponseDto;
import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.dtos.trip.TripRequestFromDriverDto;
import com.example.taxi_backend.dtos.trip.TripRequestFullDto;
import com.example.taxi_backend.dtos.trip.TripResponseForDriverDto;
import com.example.taxi_backend.entities.Log;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.mappers.TripMapper;
import com.example.taxi_backend.repositories.LogRepository;
import com.example.taxi_backend.repositories.TripRepository;
import com.example.taxi_backend.services.PriceCalculatorService;
import com.example.taxi_backend.services.RoutLengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/trip")
public class TripController {
    private AuthenticationManager authenticationManager;
    private RoutLengthService routLengthService;
    private PriceCalculatorService priceCalculatorService;
    private TripRepository tripRepository;
    private TripMapper tripMapper;
    private LogRepository logRepository;
    @Autowired
    public TripController(RoutLengthService routLengthService, PriceCalculatorService priceCalculatorService,
                          AuthenticationManager authenticationManager, TripRepository tripRepository,
                          TripMapper tripMapper, LogRepository logRepository){
        this.routLengthService = routLengthService;
        this.priceCalculatorService = priceCalculatorService;
        this.authenticationManager = authenticationManager;
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.logRepository = logRepository;
    }
    @PostMapping("calculate")
    public PriceResponseDto calculateCost(@RequestBody PriceRequestDto priceRequestDto){
        double meters = routLengthService.findRoutLength(priceRequestDto.getStartAddress(), priceRequestDto.getEndAddress());
        return new PriceResponseDto(priceCalculatorService.calculatePrice(meters, priceRequestDto.getRate()));
    }
    @GetMapping("getInfo/{id}")
    public TripResponseForDriverDto getTrip(@PathVariable long id){
        Trip trip = tripRepository.findById(id).get();

        TripResponseForDriverDto tripResponseForDriverDto = new TripResponseForDriverDto(trip);

        return tripResponseForDriverDto;
    }
    @PostMapping("end")
    public boolean endTrip(@RequestBody TripRequestFullDto tripRequestFullDto){
        Trip trip = tripMapper.mapFromFullDtoToEntity(tripRequestFullDto);

        tripRepository.save(trip);

        Log log = new Log();
        log.setMessage("Trip #" + tripRequestFullDto.getId() + " was ended.");
        logRepository.save(log);

        return true;
    }
    @GetMapping("getInProgressTrips")
    public List<Trip> getAllInProgressTrips(){
        List<Trip> trips = tripRepository.findByStatus("Created");

        if(trips != null){
            return trips;
        }

        return null;
    }
    @GetMapping("cancel/{id}")
    public void cancelTrip(@PathVariable long id){
        Trip trip = tripRepository.findById(id).get();

        trip.setStatus("Cancelled");

        tripRepository.save(trip);
    }

}
