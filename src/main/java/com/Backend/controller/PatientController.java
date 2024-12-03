package com.Backend.controller;

import com.Backend.Model.Patient;
import com.Backend.Model.User;
import com.Backend.dto.PatientDTO;
import com.Backend.dto.PatientRequest;
import com.Backend.repository.UserRepository;
import com.Backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@RequestBody PatientRequest patientRequest) {
        // Find the user by username
        User user = userRepository.findByUsername(patientRequest.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + patientRequest.getUsername());
        }

        Patient patient = new Patient();
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setAge(patientRequest.getAge());
        patient.setDateOfBirth(patientRequest.getDateOfBirth());
        patient.setGender(patientRequest.getGender());
        patient.setEmail(patientRequest.getEmail());
        patient.setPhone(patientRequest.getPhone());
        patient.setNotes(patientRequest.getNotes());
        patient.setUser(user);

        patientService.addPatient(patient);
        return ResponseEntity.ok(patient);
    }

    @PostMapping ("/user/{username}")
    public ResponseEntity<List<PatientDTO>> getPatientsByUsername(@PathVariable String username) {
        List<PatientDTO> patients = patientService.getPatientsByUsername(username);
        return ResponseEntity.ok(patients); // Return List<PatientDTO> as JSON
    }
}
