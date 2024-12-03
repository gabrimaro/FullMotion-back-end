package com.Backend.controller;

import com.Backend.Model.User;
import com.Backend.dto.UserResponseDTO;
import com.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;
    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        {
            try {
                userService.register(user); // Registers new user
                return ResponseEntity.ok("User registered successfully"); //returns message if registration is successful

            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, UserDetails authenticatedPrincipal) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()) //authenticates user with password given
            );

            if (authentication.isAuthenticated()) {
                User authenticatedUser = userService.findByUsername(user.getUsername()); //if user is authenticated it finds user

                UserResponseDTO userResponse = new UserResponseDTO( //upon login returns user info in JSON format
                        authenticatedUser.getUsername(),
                        authenticatedUser.getFirstName(),
                        authenticatedUser.getLastName(),
                        authenticatedUser.getEmail(),
                        authenticatedUser.getPrefix(),
                        authenticatedUser.getSuffix(),
                        authenticatedUser.getPatients()
                );

                return ResponseEntity.ok(userResponse);
            } else {
                // If authentication fails return an error response
                return ResponseEntity.status(401).body("Login failed: Invalid username or password");
            }
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
}
