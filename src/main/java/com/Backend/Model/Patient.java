package com.Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Patient ID number

    private String firstName;
    private String lastName;
    private int age;  // Patient age
    private String dateOfBirth; // Patient date of birth
    private String gender;      // Patient gender
    private String email; // Patient's email
    private String phone; //Patient's phone number
    private String notes; //Notes

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  //links to User entity
    private User user;
}
