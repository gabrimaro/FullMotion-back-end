package com.Backend.repository;

import com.example.healthapp.model.HeartRespiration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRespirationRepository extends JpaRepository<HeartRespiration, Long> {
    
    // Find records by heart rate above a certain threshold
    List<HeartRespiration> findByHeartRateGreaterThan(double heartRate);

    // Find all records by a specific timestamp (date)
    List<HeartRespiration> findByTimestampContaining(String date); // e.g., date = "2024-10-21"
    
    // Custom query to find average heart rate
    @Query("SELECT AVG(h.heartRate) FROM HeartRespiration h")
    Double findAverageHeartRate();
}