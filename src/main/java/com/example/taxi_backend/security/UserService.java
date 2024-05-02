package com.example.taxi_backend.security;

import com.example.taxi_backend.dtos.LoginDto;
import com.example.taxi_backend.dtos.user.UserDto;
import com.example.taxi_backend.entities.Customer;
import com.example.taxi_backend.entities.Driver;
import com.example.taxi_backend.entities.Role;
import com.example.taxi_backend.entities.User;
import com.example.taxi_backend.repositories.CustomerRepository;
import com.example.taxi_backend.repositories.DriverRepository;
import com.example.taxi_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private DriverRepository driverRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public UserService(UserRepository userRepository, DriverRepository driverRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
    public UserDto authUserRequest(LoginDto loginUser){
        User user = userRepository.findByUsername(loginUser.getUsername()).get();

        if(user.getRole() == Role.USER){
            Customer customer = customerRepository.findByEmail(loginUser.getUsername());
            return new UserDto(customer.getId(), user.getUsername(),user.getRole());
        }
        else if(user.getRole() == Role.DRIVER){
            Driver driver = driverRepository.findByEmail(loginUser.getUsername());
            return new UserDto(driver.getId(), user.getUsername(),user.getRole());
        }
        return new UserDto(user.getId(), user.getUsername(),user.getRole());
    }
}
