package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.trip.DriverResponseDto;
import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.dtos.trip.TripRequestFromDriverDto;
import com.example.taxi_backend.entities.Car;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Log;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.mappers.TripMapper;
import com.example.taxi_backend.repositories.CarRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import com.example.taxi_backend.repositories.LogRepository;
import com.example.taxi_backend.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class OrderController {
    private TripRepository tripRepository;
    private DriverRepository driverRepository;
    private CarRepository carRepository;
    private TripMapper tripMapper;
    private LogRepository logRepository;
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    public OrderController(TripRepository tripRepository, TripMapper tripMapper,
                           DriverRepository driverRepository, SimpMessagingTemplate messagingTemplate,
                           CarRepository carRepository, LogRepository logRepository){
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.driverRepository = driverRepository;
        this.messagingTemplate = messagingTemplate;
        this.carRepository = carRepository;
        this.logRepository = logRepository;
    }
    @MessageMapping("/createTrip")
    @SendTo("/topic/public")
    public Trip creteTrip(@Payload TripRequestDto tripRequestDto){
        Trip trip = tripMapper.mapTripDtoToEntity(tripRequestDto);

        Trip createdTrip = tripRepository.save(trip);
        Trip tripToReturn = tripRepository.findById(createdTrip.getId()).get();

        Log log = new Log();
        log.setMessage("Customer #" + tripRequestDto.getCustomerId() + " ordered a trip #" + tripToReturn.getId() + ".");
        logRepository.save(log);

        return tripToReturn;
    }
    @MessageMapping("/applyTrip/{id}")
    public void applyTrip(@Payload TripRequestFromDriverDto tripDto, @DestinationVariable Long id){
        Trip trip = tripMapper.mapTripDriverDtoToEntity(tripDto);

        tripRepository.save(trip);

        Driver driver = driverRepository.findById(tripDto.getDriverId()).get();
        Car car = carRepository.findById(tripDto.getDriverId()).get();

        Log log = new Log();
        log.setMessage("Driver #" + tripDto.getDriverId() + " applied a trip #" + tripDto.getId() + ".");
        logRepository.save(log);

        messagingTemplate.convertAndSend("/topic/public1/" + id,
                new DriverResponseDto(
                        driver.getId(),
                        driver.getName(),
                        driver.getPhoneNumber(),
                        car.getLicensePlate(),
                        false,
                        false));

    }

    @MessageMapping("/arrived/{id}")
    public void driverArrived(@Payload long driverId, @DestinationVariable Long id){
        Driver driver = driverRepository.findById(driverId).get();
        Car car = carRepository.findById(driverId).get();

        messagingTemplate.convertAndSend("/topic/public1/" + id, new DriverResponseDto(driver.getId(), driver.getName(), driver.getPhoneNumber(), car.getLicensePlate(),true,  false));
    }
    @MessageMapping("/end/{id}")
    public void endTrip(@Payload long driverId, @DestinationVariable Long id){
        Driver driver = driverRepository.findById(driverId).get();
        Car car = carRepository.findById(driverId).get();

        messagingTemplate.convertAndSend("/topic/public1/" + id, new DriverResponseDto(driver.getId(), driver.getName(), driver.getPhoneNumber(), car.getLicensePlate(),true,  true));
    }

    @MessageMapping("/cancel")
    public void cancelTrip(@Payload long tripId){
        Trip trip = tripRepository.findById(tripId).get();

        trip.setStatus("Cancelled");

        tripRepository.save(trip);

        Log log = new Log();
        log.setMessage("Trip #" + tripId + "was cancelled.");
        logRepository.save(log);

        messagingTemplate.convertAndSend("/topic/public2", true);

    }
}
