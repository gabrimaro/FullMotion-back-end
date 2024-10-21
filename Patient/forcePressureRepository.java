package com.Backend.repository;

import com.example.healthapp.model.ForcePressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForcePressureRepository extends JpaRepository<ForcePressure, Long> {
    
    // Find all records by exerciseID
    List<ForcePressure> findByExerciseID(Long exerciseID);
    
    // Custom query to find records with muscle activation above a threshold
    List<ForcePressure> findByMuscleActivationGreaterThan(double muscleActivation);

}
