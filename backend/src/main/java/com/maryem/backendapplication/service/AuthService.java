package com.maryem.backendapplication.service;

import com.maryem.backendapplication.model.User;
import com.maryem.backendapplication.respository.UserRepository;
import com.maryem.backendapplication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        // Check if user already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Hash password before saving
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        // Save user
        User savedUser = userRepository.save(user);

        // Generate JWT token
        return jwtUtil.generateToken(savedUser.getUsername());
    }

    public String loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtUtil.generateToken(username);
    }
}