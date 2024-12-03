package com.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;  // Patient ID
    private String firstName;  // Patient first name
    private String lastName;   // Patient last name
    private int age;  // Patient age
    private String dateOfBirth; // Patient date of birth
    private String gender;      // Patient gender
    private String email; // Patient's email
    private String phone; //Patient's phone number
    private String notes; //Notes


}
