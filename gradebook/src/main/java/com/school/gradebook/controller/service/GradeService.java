package com.school.gradebook.controller.service;

import com.school.gradebook.controller.repository.GradeRepository;
import com.school.gradebook.model.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }
}
