package com.Backend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Temperature/Sweat")
@Getter
@Setter
public class TempSweat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempSweatID;

    private double temperature;
    private double sweatRate;
    private String timestamp;
}
