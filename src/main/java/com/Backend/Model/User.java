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
}

@Entity
@Table(name = "Posture/Balance")
@Getter
@Setter
public class PostureBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balanceID;

    private Long sessionID;
    private String bodyAlignment;
    private String centerOfMass;
    private String swayPattern;
    private double balanceTime;
    private boolean supportNeeded;
}

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

