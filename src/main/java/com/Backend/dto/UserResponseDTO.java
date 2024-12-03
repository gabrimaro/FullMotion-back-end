package com.Backend.dto;

import com.Backend.Model.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String prefix;
    private String suffix;
    private List<Patient> patients;

    public UserResponseDTO(String username, String firstName, String lastName, String email, String prefix, String suffix, List<Patient> patients) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.prefix = prefix;
        this.suffix = suffix;
        this.patients = patients;
    }
}