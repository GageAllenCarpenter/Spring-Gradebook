package com.school.gradebook.controller.service;

import com.school.gradebook.controller.repository.SemesterRepository;
import com.school.gradebook.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public List<Semester> getSemesters() {
        return semesterRepository.findAll();
    }
}
