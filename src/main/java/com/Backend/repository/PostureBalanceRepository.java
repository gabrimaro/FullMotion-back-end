package com.Backend.repository;

import com.Backend.Model.PostureBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostureBalanceRepository extends JpaRepository<PostureBalance, Long> {
    
    // Find all records by a specific sessionID
    List<PostureBalance> findBySessionID(Long sessionID);
    
    // Custom query to find records where support is needed
    @Query("SELECT p FROM PostureBalance p WHERE p.supportNeeded = true")
    List<PostureBalance> findAllWithSupportNeeded();

    // Custom query to find by a balance time greater than a specific value
    List<PostureBalance> findByBalanceTimeGreaterThan(double balanceTime);
}