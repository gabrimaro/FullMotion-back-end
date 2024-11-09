package com.Backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Force/Pressure")
@Getter
@Setter
public class ForcePressure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forcePressureID;

    private Long exerciseID;
    private double muscleActivation;
    private double pressureDistribution;
    private double positionTime;
    private String timestamp;
}
