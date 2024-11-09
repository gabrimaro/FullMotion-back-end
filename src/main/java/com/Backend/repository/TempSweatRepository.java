package com.Backend.repository;

import com.Backend.Model.TempSweat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempSweatRepository extends JpaRepository<TempSweat, Long> {
    
    // Find all records with temperature above a specific value
    List<TempSweat> findByTemperatureGreaterThan(double temperature);
    
    // Find records with a specific sweat rate range
    List<TempSweat> findBySweatRateBetween(double minRate, double maxRate);
}