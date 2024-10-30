package com.Backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Repititions")
@Getter
@Setter
public class RepsDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repsDurationID;

    private int reps;
    private double duration;
    private String timestamp;
}
