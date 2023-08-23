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

    public Semester getSemesterById(Long id) {
        return semesterRepository.findById(id).orElse(null);
    }

    public void addSemester(Semester semester) {
        semesterRepository.save(semester);
    }

    public void updateSemester(Semester semester) {
        semesterRepository.save(semester);
    }

    public void deleteSemester(Long id) {
        semesterRepository.deleteById(id);
    }
}
