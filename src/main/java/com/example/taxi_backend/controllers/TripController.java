package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.price.PriceRequestDto;
import com.example.taxi_backend.dtos.price.PriceResponseDto;
import com.example.taxi_backend.dtos.trip.TripRequestDto;
import com.example.taxi_backend.services.PriceCalculatorService;
import com.example.taxi_backend.services.RoutLengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trip")
public class TripController {
    private AuthenticationManager authenticationManager;
    private RoutLengthService routLengthService;
    private PriceCalculatorService priceCalculatorService;
    @Autowired
    public TripController(RoutLengthService routLengthService, PriceCalculatorService priceCalculatorService, AuthenticationManager authenticationManager){
        this.routLengthService = routLengthService;
        this.priceCalculatorService = priceCalculatorService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("calculate")
    public PriceResponseDto calculateCost(@RequestBody PriceRequestDto priceRequestDto){
        double meters = routLengthService.findRoutLength(priceRequestDto.getStartAddress(), priceRequestDto.getEndAddress());
        return new PriceResponseDto(priceCalculatorService.calculatePrice(meters, priceRequestDto.getRate()));
    }
    @PostMapping("create")
    public void saveTrip(@RequestBody TripRequestDto tripRequestDto){

    }

}
