package com.Backend.service;

import com.Backend.Model.Patient;
import com.Backend.Model.User;
import com.Backend.repository.PatientRepository;
import com.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public void addPatient(Patient patient) {
        Optional<User> user = userRepository.findById(patient.getUser().getId());
        if (user.isPresent()) {
            patient.setUser(user.get());
            patientRepository.save(patient);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public List<Patient> getPatientsByUser(Long userId) {
        return patientRepository.findByUser_Id(userId);
    }
}
