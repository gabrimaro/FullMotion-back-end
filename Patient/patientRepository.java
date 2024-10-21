package com.Backend.repository;

import com.example.healthapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Find a patient by their last name
    List<Patient> findByLastName(String lastName);

    // Find all patients with a specific gender
    List<Patient> findByGender(String gender);
    
    // Custom query to find patients by age greater than a specified value
    List<Patient> findByAgeGreaterThan(int age);
    
    // Custom query to find patients by their full name (firstName and lastName)
    @Query("SELECT p FROM Patient p WHERE p.firstName = ?1 AND p.lastName = ?2")
    List<Patient> findByFullName(String firstName, String lastName);

}
