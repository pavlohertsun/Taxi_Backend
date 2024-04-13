package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.mappers.TripMapper;
import com.example.taxi_backend.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    private TripRepository tripRepository;
    private TripMapper tripMapper;
    @Autowired
    public OrderController(TripRepository tripRepository, TripMapper tripMapper){
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }
    @MessageMapping("/createTrip")
    @SendTo("/topic/public")
    public Trip creteTrip(@Payload TripRequestDto tripRequestDto){
        Trip trip = tripMapper.mapTripDtoToEntity(tripRequestDto);

        Trip createdTrip = tripRepository.save(trip);

        return createdTrip;
    }
}
