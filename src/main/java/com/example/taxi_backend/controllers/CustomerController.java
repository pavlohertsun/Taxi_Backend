package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.CustomerDto;
import com.example.taxi_backend.dtos.balance.TopUpBalanceDto;
import com.example.taxi_backend.dtos.trip.TripResponseDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Log;
import com.example.taxi_backend.entities.Trip;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.GetUserInfoJsonRepository;
import com.example.taxi_backend.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/customer/")
public class CustomerController {
    private CustomerRepository customerRepository;
    private GetUserInfoJsonRepository userInfoJsonRepository;
    private LogRepository logRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, GetUserInfoJsonRepository userInfoJsonRepository, LogRepository logRepository) {
        this.customerRepository = customerRepository;
        this.userInfoJsonRepository = userInfoJsonRepository;
        this.logRepository = logRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id){
        Customer customer = customerRepository.findById(id).get();

        if(customer != null){
            CustomerDto customerDto = new CustomerDto(customer);
            return ResponseEntity.ok(customerDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/trips")
    public ResponseEntity<Set<TripResponseDto>> getSetOfTrips(@PathVariable Long id){
        Set<Trip> trips = customerRepository.findById(id).get().getTrips();

        Set<TripResponseDto> tripResponseDtos = new HashSet<>();

        for(Trip trip : trips){
            tripResponseDtos.add(new TripResponseDto(trip));
        }

        return ResponseEntity.ok(tripResponseDtos);
    }
    @PostMapping("/balance")
    public ResponseEntity<Boolean> topUpBalance(@RequestBody TopUpBalanceDto topUpBalanceDto){
        Customer customer = customerRepository.findById(topUpBalanceDto.getUserId()).get();


        if(customer != null){
            customer.setBalance(customer.getBalance() + topUpBalanceDto.getSum());

            customerRepository.save(customer);

            Log log = new Log();
            log.setMessage("Customer #" + topUpBalanceDto.getUserId() + "top up balance by: " + topUpBalanceDto.getSum() + ".");
            logRepository.save(log);

            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("{id}/balance")
    public double getCustomerBalance(@PathVariable long id){
        Customer customer = customerRepository.findById(id).get();

        return customer.getBalance();
    }

    @GetMapping("/downloadUserData/{id}")
    public ResponseEntity<byte[]> downloadUserData(@PathVariable long id) {
        String jsonData = userInfoJsonRepository.getCustomerData(id);

        byte[] jsonDataBytes = jsonData.getBytes();

        Log log = new Log();
        log.setMessage("Customer #" + id + "got his info in Json");
        logRepository.save(log);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Disposition", "attachment; filename=customerData.json")
                .body(jsonDataBytes);
    }
}
