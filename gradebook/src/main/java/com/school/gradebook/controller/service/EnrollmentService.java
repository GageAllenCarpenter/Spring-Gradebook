package com.school.gradebook.controller.service;

import com.school.gradebook.controller.repository.EnrollmentRepository;
import com.school.gradebook.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getEnrollments() {
        return enrollmentRepository.findAll();
    }
}
