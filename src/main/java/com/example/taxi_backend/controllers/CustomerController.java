package com.example.taxi_backend.controllers;

import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.User;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/")
public class CustomerController {
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Customer customer = customerRepository.findById(id).get();
        if(customer != null){
            return ResponseEntity.ok(customer);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
