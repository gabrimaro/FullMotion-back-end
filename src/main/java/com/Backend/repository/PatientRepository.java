package com.Backend.repository;

import com.Backend.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByUser_Id(Long userId);
    // You can add queries specific to patients if needed
}