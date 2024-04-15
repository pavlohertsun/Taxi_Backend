package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.trip.DriverResponseDto;
import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.dtos.trip.TripRequestFromDriverDto;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.mappers.TripMapper;
import com.example.taxi_backend.repositories.DriverRepository;
import com.example.taxi_backend.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    private TripRepository tripRepository;
    private DriverRepository driverRepository;
    private TripMapper tripMapper;
    @Autowired
    public OrderController(TripRepository tripRepository, TripMapper tripMapper, DriverRepository driverRepository){
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.driverRepository = driverRepository;
    }
    @MessageMapping("/createTrip")
    @SendTo("/topic/public")
    public Trip creteTrip(@Payload TripRequestDto tripRequestDto){
        Trip trip = tripMapper.mapTripDtoToEntity(tripRequestDto);

        Trip createdTrip = tripRepository.save(trip);
        Trip tripToReturn = tripRepository.findById(createdTrip.getId()).get();

        return tripToReturn;
    }
    @MessageMapping("/applyTrip")
    @SendTo("/topic/public1")
    public DriverResponseDto applyTrip(@Payload TripRequestFromDriverDto tripDto){
        Trip trip = tripMapper.mapTripDriverDtoToEntity(tripDto);

        tripRepository.save(trip);

        Driver driver = driverRepository.findById(tripDto.getDriverId()).get();

        return new DriverResponseDto(driver.getName(), driver.getPhoneNumber(), false,  false);
    }

    @MessageMapping("/arrived")
    @SendTo("/topic/public1")
    public DriverResponseDto driverArrived(@Payload long id){
        Driver driver = driverRepository.findById(id).get();

        return new DriverResponseDto(driver.getName(), driver.getPhoneNumber(), true, false);
    }
    @MessageMapping("/end")
    @SendTo("/topic/public1")
    public DriverResponseDto endTrip(@Payload long id){
        Driver driver = driverRepository.findById(id).get();

        return new DriverResponseDto(driver.getName(), driver.getPhoneNumber(), true, true);
    }

    @MessageMapping("/cancel")
    @SendTo("/topic/public2")
    public boolean cancelTrip(@Payload long id){
        Trip trip = tripRepository.findById(id).get();

        trip.setStatus("Cancelled");

        tripRepository.save(trip);

        return true;
    }
}
