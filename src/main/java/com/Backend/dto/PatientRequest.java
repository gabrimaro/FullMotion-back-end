package com.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequest {
    private String firstName;
    private String lastName;
    private int age;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String notes;
    private String username; // Only the username is required for the User
}
