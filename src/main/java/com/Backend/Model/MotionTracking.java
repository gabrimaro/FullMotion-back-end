package com.Backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Motion Tracking")
@Getter
@Setter
public class MotionTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingID;

    private Long exerciseID;
    private double jointAngle;
    private double rangeOfMotion;
    private double movementSpeed;
    private double stepLength;
    private int cadence;
    private String timestamp;
}
