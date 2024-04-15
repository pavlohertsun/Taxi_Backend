package com.example.taxi_backend.mappers;

import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.dtos.trip.TripRequestFromDriverDto;
import com.example.taxi_backend.dtos.trip.TripRequestFullDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;

    public TripMapper(CustomerRepository customerRepository, DriverRepository driverRepository) {
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
    }

    public Trip mapTripDtoToEntity(TripRequestDto tripRequestDto){
        Customer customer = customerRepository.findById(tripRequestDto.getCustomerId()).get();
        Trip trip = new Trip();

        trip.setStartPoint(tripRequestDto.getStartPoint());
        trip.setEndPoint(tripRequestDto.getEndPoint());
        trip.setPrice(tripRequestDto.getPrice());
        trip.setStatus(tripRequestDto.getStatus());
        trip.setRate(tripRequestDto.getRate());
        trip.setDescription(tripRequestDto.getDescription());
        trip.setUser(customer);

        return trip;
    }
    public Trip mapTripDriverDtoToEntity(TripRequestFromDriverDto tripRequestFromDriverDto){
        Driver driver = driverRepository.findById(tripRequestFromDriverDto.getDriverId()).get();
        Customer customer = customerRepository.findById(tripRequestFromDriverDto.getCustomerId()).get();
        Trip trip = new Trip();

        trip.setId(tripRequestFromDriverDto.getId());
        trip.setStartTime(tripRequestFromDriverDto.getStartTime());
        trip.setStartPoint(tripRequestFromDriverDto.getStartPoint());
        trip.setEndPoint(tripRequestFromDriverDto.getEndPoint());
        trip.setPrice(tripRequestFromDriverDto.getPrice());
        trip.setStatus(tripRequestFromDriverDto.getStatus());
        trip.setRate(tripRequestFromDriverDto.getRate());
        trip.setDescription(tripRequestFromDriverDto.getDescription());
        trip.setUser(customer);
        trip.setDriver(driver);

        return trip;
    }
    public Trip mapFromFullDtoToEntity(TripRequestFullDto tripRequestFullDto){
        Driver driver = driverRepository.findById(tripRequestFullDto.getDriverId()).get();
        Customer customer = customerRepository.findById(tripRequestFullDto.getCustomerId()).get();
        Trip trip = new Trip();

        trip.setId(tripRequestFullDto.getId());
        trip.setStartTime(tripRequestFullDto.getStartTime());
        trip.setStartPoint(tripRequestFullDto.getStartPoint());
        trip.setEndPoint(tripRequestFullDto.getEndPoint());
        trip.setPrice(tripRequestFullDto.getPrice());
        trip.setStatus(tripRequestFullDto.getStatus());
        trip.setRate(tripRequestFullDto.getRate());
        trip.setDescription(tripRequestFullDto.getDescription());
        trip.setUser(customer);
        trip.setDriver(driver);

        return trip;
    }
}
