package com.Backend.service;

import com.Backend.Model.User;
import com.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) { // Checks if user exists
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) { //checks if email has been used
            throw new IllegalArgumentException("Email already exists");
        }
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Sets password for new user
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // Finds user based on username
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email); //Finds user by email
    }

    public boolean authenticate(String username, String password) {
        User user = findByUsername(username); // Finds the user
        if (user != null) {
            // Compares the entered password with the stored encrypted password
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false; // If user not found or password does not match
    }
}