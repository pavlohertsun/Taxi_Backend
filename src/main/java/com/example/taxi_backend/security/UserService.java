package com.example.taxi_backend.security;

import com.example.taxi_backend.dtos.LoginDto;
import com.example.taxi_backend.dtos.UserDto;
import com.example.taxi_backend.entities.User;
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
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        return new UserDto(user.getUsername(),user.getRole());
    }
}
