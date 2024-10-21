package com.example.healthapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String Gender;
    private String phoneNumber;
    private String email;
    private String address;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

@Entity
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

    public Long getBalanceID() {
        return balanceID;
    }

    public void setBalanceID(Long balanceID) {
        this.balanceID = balanceID;
    }

    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public String getBodyAlignment() {
        return bodyAlignment;
    }

    public void setBodyAlignment(String bodyAlignment) {
        this.bodyAlignment = bodyAlignment;
    }

    public String getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(String centerOfMass) {
        this.centerOfMass = centerOfMass;
    }

    public String getSwayPattern() {
        return swayPattern;
    }

    public void setSwayPattern(String swayPattern) {
        this.swayPattern = swayPattern;
    }

    public double getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(double balanceTime) {
        this.balanceTime = balanceTime;
    }

    public boolean isSupportNeeded() {
        return supportNeeded;
    }

    public void setSupportNeeded(boolean supportNeeded) {
        this.supportNeeded = supportNeeded;
    }
}

@Entity
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

    public Long getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(Long trackingID) {
        this.trackingID = trackingID;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public double getJointAngle() {
        return jointAngle;
    }

    public void setJointAngle(double jointAngle) {
        this.jointAngle = jointAngle;
    }

    public double getRangeOfMotion() {
        return rangeOfMotion;
    }

    public void setRangeOfMotion(double rangeOfMotion) {
        this.rangeOfMotion = rangeOfMotion;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public double getStepLength() {
        return stepLength;
    }

    public void setStepLength(double stepLength) {
        this.stepLength = stepLength;
    }

    public int getCadence() {
        return cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

@Entity
public class ForcePressure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forcePressureID;

    private Long exerciseID;
    private double muscleActivation;
    private double pressureDistribution;
    private double positionTime;
    private String timestamp;

    public Long getForcePressureID() {
        return forcePressureID;
    }

    public void setForcePressureID(Long forcePressureID) {
        this.forcePressureID = forcePressureID;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public double getMuscleActivation() {
        return muscleActivation;
    }

    public void setMuscleActivation(double muscleActivation) {
        this.muscleActivation = muscleActivation;
    }

    public double getPressureDistribution() {
        return pressureDistribution;
    }

    public void setPressureDistribution(double pressureDistribution) {
        this.pressureDistribution = pressureDistribution;
    }

    public double getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(double positionTime) {
        this.positionTime = positionTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

@Entity
public class HeartRespiration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartRespirationID;

    private double heartRate;
    private double respirationRate;
    private String timestamp;


    public Long getHeartRespirationID() {
        return heartRespirationID;
    }

    public void setHeartRespirationID(Long heartRespirationID) {
        this.heartRespirationID = heartRespirationID;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public double getRespirationRate() {
        return respirationRate;
    }

    public void setRespirationRate(double respirationRate) {
        this.respirationRate = respirationRate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

@Entity
public class RepsDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repsDurationID;

    private int reps;
    private double duration;
    private String timestamp;

    public Long getRepsDurationID() {
        return repsDurationID;
    }

    public void setRepsDurationID(Long repsDurationID) {
        this.repsDurationID = repsDurationID;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

public class TempSweat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempSweatID;

    private double temperature;
    private double sweatRate;
    private String timestamp;

    public Long getTempSweatID() {
        return tempSweatID;
    }

    public void setTempSweatID(Long tempSweatID) {
        this.tempSweatID = tempSweatID;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getSweatRate() {
        return sweatRate;
    }

    public void setSweatRate(double sweatRate) {
        this.sweatRate = sweatRate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

