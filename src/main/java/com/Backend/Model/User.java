package com.Backend.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // User ID number

    private String username;
    private String password;
    private String prefix;
    private String firstName;
    private String lastName;
    private String suffix;
    private String email;
}



