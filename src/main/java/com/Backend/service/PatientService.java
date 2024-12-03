package com.Backend.service;

import com.Backend.Model.Patient;
import com.Backend.Model.User;
import com.Backend.dto.PatientDTO;
import com.Backend.repository.PatientRepository;
import com.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public void addPatient(Patient patient) {
        // Find the user by their username from the patient data
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(patient.getUser().getUsername()));
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with username: " + patient.getUser().getUsername());
        }

        User user = userOptional.get();

        // Set the user to the patient
        patient.setUser(user);

        // Save the patient
        patientRepository.save(patient);
    }

    public List<PatientDTO> getPatientsByUsername(String username) {
        // Find the user by username
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userOptional.get();

        // Fetch patients associated with the found user
        List<Patient> patients = patientRepository.findByUser(user);  // Assuming a method in PatientRepository for this
        return patients.stream()
                .map(patient -> new PatientDTO(
                        patient.getId(),
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getAge(),
                        patient.getDateOfBirth(),
                        patient.getGender(),
                        patient.getEmail(),
                        patient.getPhone(),
                        patient.getNotes()))
                .collect(Collectors.toList());
    }
}

