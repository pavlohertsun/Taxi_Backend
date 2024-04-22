package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.*;
import com.example.taxi_backend.dtos.auth_responce.AuthResponseDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Role;
import com.example.taxi_backend.entities.User;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import com.example.taxi_backend.repositories.UserRepository;
import com.example.taxi_backend.security.JwtService;
import com.example.taxi_backend.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final UserService userService;
    private JwtService jwtService;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder passwordEncoder, JwtService jwtGenerator,
                          UserRepository userRepository, CustomerRepository customerRepository, DriverRepository driverRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtGenerator;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> authUser(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        return ResponseEntity
                .ok()
                .body(
                        new AuthResponseDto(jwtService.createToken(loginDto).get()
                                , userService.authUserRequest(loginDto))
                );
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterCustomerDto registerCustomerDto) {
        User user = new User();

        user.setUsername(registerCustomerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerCustomerDto.getPassword())));
        user.setRole(registerCustomerDto.getRole().equals("User") ? Role.USER : Role.DRIVER);

        User createdUser = userRepository.save(user);

        if(registerCustomerDto.getRole().equals("User")){
            Customer customer = new Customer();
            customer.setId(createdUser.getId());
            customer.setName(registerCustomerDto.getName());
            customer.setSurname(registerCustomerDto.getSurname());
            customer.setEmail(registerCustomerDto.getUsername());
            customer.setPhoneNumber(registerCustomerDto.getPhoneNumber());
            customer.setBalance(0);
            customer.setRating(5.0);

            customerRepository.save(customer);

        }
        else{
            Driver driver = new Driver();
            driver.setId(createdUser.getId());
            driver.setName(registerCustomerDto.getName());
            driver.setSurname(registerCustomerDto.getSurname());
            driver.setEmail(registerCustomerDto.getUsername());
            driver.setPhoneNumber(registerCustomerDto.getPhoneNumber());
            driver.setBalance(0);
            driver.setLicense(false);
            driver.setStatus("Non-authenticated");

            driverRepository.save(driver);
        }

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
