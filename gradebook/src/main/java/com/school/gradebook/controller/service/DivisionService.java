package com.school.gradebook.controller.service;

import com.school.gradebook.controller.repository.DivisionRepository;
import com.school.gradebook.model.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionService {
    private final DivisionRepository divisionRepository;

    @Autowired
    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public List<Division> getDivisions() {
        return divisionRepository.findAll();
    }

    public Division getDivisionById(Long id) {
        return divisionRepository.findById(id).orElse(null);
    }

    public void addDivision(Division division) {
        divisionRepository.save(division);
    }

    public void updateDivision(Division division) {
        divisionRepository.save(division);
    }

    public void deleteDivision(Long id) {
        divisionRepository.deleteById(id);
    }
}
