package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.*;
import com.example.taxi_backend.dtos.auth_responce.AuthResponseDto;
import com.example.taxi_backend.entities.*;
import com.example.taxi_backend.repositories.*;
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
    private LogRepository logRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder passwordEncoder, JwtService jwtGenerator,
                          UserRepository userRepository, CustomerRepository customerRepository, DriverRepository driverRepository,
                          LogRepository logRepository, EmployeeRepository employeeRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtGenerator;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.logRepository = logRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> authUser(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        Log log = new Log();
        log.setMessage("User with email: " + loginDto.getUsername() + " authorized.");
        logRepository.save(log);

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

        if(registerCustomerDto.getRole().equals("User")){
            user.setRole(Role.USER);

            userRepository.save(user);

            Customer customer = new Customer();
            customer.setName(registerCustomerDto.getName());
            customer.setSurname(registerCustomerDto.getSurname());
            customer.setEmail(registerCustomerDto.getUsername());
            customer.setPhoneNumber(registerCustomerDto.getPhoneNumber());
            customer.setBalance(0);
            customer.setRating(5.0);

            Customer created = customerRepository.save(customer);

            Log log = new Log();
            log.setMessage("New customer registered with id: " + created.getId() + ".");
            logRepository.save(log);

        }
        else if (registerCustomerDto.getRole().equals("Driver")){
            user.setRole(Role.DRIVER);

            userRepository.save(user);

            Driver driver = new Driver();
            driver.setName(registerCustomerDto.getName());
            driver.setSurname(registerCustomerDto.getSurname());
            driver.setEmail(registerCustomerDto.getUsername());
            driver.setPhoneNumber(registerCustomerDto.getPhoneNumber());
            driver.setBalance(0);
            driver.setLicense(false);
            driver.setStatus("Non-authenticated");

            Driver created = driverRepository.save(driver);

            Log log = new Log();
            log.setMessage("New driver registered with id: " + created.getId() + ".");
            logRepository.save(log);
        }
        else if(registerCustomerDto.getRole().equals("Worker")){
            user.setRole(Role.WORKER);

            userRepository.save(user);

            Employee employee = new Employee();
            employee.setName(registerCustomerDto.getName());
            employee.setSurname(registerCustomerDto.getSurname());
            employee.setEmail(registerCustomerDto.getUsername());
            employee.setPosition("Support");
            employee.setSalary(8000);

            employeeRepository.save(employee);
        }
        else if (registerCustomerDto.getRole().equals("Admin")) {
            user.setRole(Role.ADMIN);

            userRepository.save(user);

            Employee employee = new Employee();
            employee.setName(registerCustomerDto.getName());
            employee.setSurname(registerCustomerDto.getSurname());
            employee.setEmail(registerCustomerDto.getUsername());
            employee.setPosition("Admin");
            employee.setSalary(10000);

            employeeRepository.save(employee);
        }

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
