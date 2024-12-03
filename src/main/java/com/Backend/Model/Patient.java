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
    private String dateOfBirth;
    private String gender;
    private String medicalHistory;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Link to User entity
    private User user;  // Each patient is associated with one user
}
