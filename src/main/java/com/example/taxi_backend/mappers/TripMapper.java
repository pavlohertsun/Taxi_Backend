package com.example.taxi_backend.mappers;

import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {
    private CustomerRepository customerRepository;

    public TripMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Trip mapTripDtoToEntity(TripRequestDto tripRequestDto){
        Customer customer = customerRepository.findById(tripRequestDto.getCustomerId()).get();
        Trip trip = new Trip();

        trip.setStartTime(tripRequestDto.getStartTime());
        trip.setStartPoint(tripRequestDto.getStartPoint());
        trip.setEndPoint(tripRequestDto.getEndPoint());
        trip.setPrice(tripRequestDto.getPrice());
        trip.setStatus(tripRequestDto.getStatus());
        trip.setRate(tripRequestDto.getRate());
        trip.setDescription(tripRequestDto.getDescription());
        trip.setUser(customer);

        return trip;
    }
}
