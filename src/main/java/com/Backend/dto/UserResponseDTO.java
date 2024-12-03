package com.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String prefix;
    private String suffix;

    public UserResponseDTO(String username, String firstName, String lastName, String email, String prefix, String suffix) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.prefix = prefix;
        this.suffix = suffix;
    }
}