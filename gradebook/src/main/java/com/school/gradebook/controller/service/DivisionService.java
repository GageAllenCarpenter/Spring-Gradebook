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
}
