package com.Backend.controller;

import com.Backend.Model.Patient;
import com.Backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/therapist/{therapistId}")
    public List<Patient> getPatientsByTherapistId(@PathVariable Long therapistId) {
        return patientService.getPatientsByTherapistId(therapistId);
    }

    @PostMapping ("/add")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }
}
