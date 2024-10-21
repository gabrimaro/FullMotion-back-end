package com.Backend.repository;

import com.example.healthapp.model.RepsDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepsDurationRepository extends JpaRepository<RepsDuration, Long> {
    
    // Find records by a minimum number of reps
    List<RepsDuration> findByRepsGreaterThanEqual(int reps);

    // Find records by a specific duration range
    List<RepsDuration> findByDurationBetween(double minDuration, double maxDuration);
    
}