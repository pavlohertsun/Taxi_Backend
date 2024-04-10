package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.AuthResponseDto;
import com.example.taxi_backend.dtos.LoginDto;
import com.example.taxi_backend.dtos.RegisterDto;
import com.example.taxi_backend.entities.User;
import com.example.taxi_backend.repositories.UserRepository;
import com.example.taxi_backend.security.JwtService;
import com.example.taxi_backend.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<?> authUser(@RequestBody LoginDto loginUser) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()));

            return ResponseEntity
                    .ok()
                    .body(
                            new AuthResponseDto(jwtService.createToken(loginUser).get()
                                    , userService.authUserRequest(loginUser))
                    );
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        user.setRole(User.Role.USER);

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
