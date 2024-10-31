package com.Backend.repository;

import com.Backend.Model.MotionTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotionTrackingRepository extends JpaRepository<MotionTracking, Long> {
    
    // Find all tracking data by exerciseID
    List<MotionTracking> findByExerciseID(Long exerciseID);
    
    // Find records with movementSpeed greater than a specific value
    List<MotionTracking> findByMovementSpeedGreaterThan(double speed);
}