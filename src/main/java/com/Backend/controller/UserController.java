package com.Backend.controller;

import com.Backend.Model.User;
import com.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        {
            userService.register(user); // Registers new user
            return ResponseEntity.ok("User registered successfully"); //returns message if registration is successful
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                User authenticatedUser = userService.findByUsername(user.getUsername());

                UserResponseDTO userResponse = new UserResponseDTO(
                        authenticatedUser.getUsername(),
                        authenticatedUser.getFirstName(),
                        authenticatedUser.getLastName(),
                        authenticatedUser.getEmail()
                );

                return ResponseEntity.ok(userResponse);
            } else {
                // If authentication fails, return an error response
                return ResponseEntity.status(401).body("Login failed: Invalid username or password");
            }
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
}
