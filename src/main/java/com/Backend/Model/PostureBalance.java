package com.Backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Posture/Balance")
@Getter
@Setter
public class PostureBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseID;
    private String bodyAlignment;
    private String centerOfMass;
    private String swayPattern;
    private double balanceTime;
    private boolean supportNeeded;
}
