package com.Backend.controller;

import com.Backend.Model.Patient;
import com.Backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        return ResponseEntity.ok("Patient added successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Patient>> getPatientsByUser(@PathVariable Long userId) {
        List<Patient> patients = patientService.getPatientsByUser(userId);
        return ResponseEntity.ok(patients);
    }
}
