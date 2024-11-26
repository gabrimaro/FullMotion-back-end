package com.Backend.service;

import com.Backend.Model.Therapist;
import com.Backend.repository.TherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TherapistService {
    @Autowired
    private TherapistRepository therapistRepository;

    public Optional<Therapist> findByTherapistId(Long therapistId) {
        return therapistRepository.findById(therapistId);
    }
}