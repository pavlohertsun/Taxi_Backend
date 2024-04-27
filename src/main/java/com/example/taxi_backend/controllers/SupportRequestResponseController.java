package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.SupportRequestDto;
import com.example.taxi_backend.dtos.SupportRequestsForProfileDto;
import com.example.taxi_backend.dtos.SupportResponseDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Log;
import com.example.taxi_backend.entities.SupportRequest;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import com.example.taxi_backend.repositories.LogRepository;
import com.example.taxi_backend.repositories.SupportRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportRequestResponseController {

    private SupportRequestsRepository supportRequestsRepository;
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;

    private LogRepository logRepository;
    @Autowired
    public SupportRequestResponseController(SupportRequestsRepository supportRequestsRepository, CustomerRepository customerRepository, DriverRepository driverRepository, LogRepository logRepository) {
        this.supportRequestsRepository = supportRequestsRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.logRepository = logRepository;
    }

    @PostMapping("")
    public void createSupportRequest(@RequestBody SupportRequestDto supportRequestDto){
        SupportRequest supportRequest = new SupportRequest();

        supportRequest.setRequest(supportRequestDto.getRequest());
        if(supportRequestDto.getCustomerId() != 0){
            Customer customer = customerRepository.findById(supportRequestDto.getCustomerId()).get();

            supportRequest.setCustomer(customer);

            Log log = new Log();
            log.setMessage("Customer #" + supportRequestDto.getCustomerId() + " made a support request.");
            logRepository.save(log);
        }
        else if(supportRequestDto.getDriverId() != 0){
            Driver driver = driverRepository.findById(supportRequestDto.getDriverId()).get();

            supportRequest.setDriver(driver);

            Log log = new Log();
            log.setMessage("Driver #" + supportRequestDto.getDriverId() + " made a support request.");
            logRepository.save(log);
        }

        supportRequestsRepository.save(supportRequest);
    }
    @PutMapping("")
    public void setResponseToRequest(@RequestBody SupportResponseDto supportResponseDto){
        SupportRequest supportRequest = supportRequestsRepository.findById(supportResponseDto.getId()).get();

        supportRequest.setResponse(supportResponseDto.getResponse());
        supportRequest.setStatus(supportResponseDto.getStatus());

        supportRequestsRepository.save(supportRequest);
    }
    @GetMapping("")
    public List<SupportRequestsForProfileDto> getAllProcessingRequests(){
        List<SupportRequest> supportRequests = supportRequestsRepository.findAllByStatus("Processing");

        List<SupportRequestsForProfileDto> supportRequestsForProfileDtos = new ArrayList<>();

        for(SupportRequest supportRequest : supportRequests){
            SupportRequestsForProfileDto supportRequestsForProfileDto = new SupportRequestsForProfileDto();

            supportRequestsForProfileDto.setId(supportRequest.getId());
            supportRequestsForProfileDto.setRequest(supportRequest.getRequest());

            if(supportRequest.getCustomer() != null){
                supportRequestsForProfileDto.setUserId(supportRequest.getCustomer().getId());
                supportRequestsForProfileDto.setUserName(supportRequest.getCustomer().getName());
            }
            else if(supportRequest.getDriver() != null){
                supportRequestsForProfileDto.setUserId(supportRequest.getDriver().getId());
                supportRequestsForProfileDto.setUserName(supportRequest.getDriver().getName());
            }

            supportRequestsForProfileDtos.add(supportRequestsForProfileDto);
        }

        return supportRequestsForProfileDtos;
    }
}
