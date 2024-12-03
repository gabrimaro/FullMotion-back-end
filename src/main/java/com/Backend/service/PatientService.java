package com.Backend.service;

import com.Backend.Model.Patient;
import com.Backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    public List<Patient> getPatientsByTherapistId(Long therapistId) {
        return patientRepository.findByTherapistId(therapistId);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findPatientById(patientId);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);

    }
}
