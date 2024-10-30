package com.Backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Heart Rate/Respiration")
@Getter
@Setter
public class HeartRespiration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartRespirationID;

    private double heartRate;
    private double respirationRate;
    private String timestamp;
}
