package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.*;
import com.example.taxi_backend.entities.User;
import com.example.taxi_backend.repositories.UserRepository;
import com.example.taxi_backend.security.JwtService;
import com.example.taxi_backend.security.UserService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final UserService userService;
    private JwtService jwtService;
    private UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder passwordEncoder, JwtService jwtGenerator, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtGenerator;
        this.userRepository = userRepository;
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
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        User user = new User();

        user.setName(registerDto.getName());
        user.setSurname(registerDto.getSurname());
        user.setUsername(registerDto.getUsername());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setRole(User.Role.USER);
        user.setRating(registerDto.getRating());
        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
