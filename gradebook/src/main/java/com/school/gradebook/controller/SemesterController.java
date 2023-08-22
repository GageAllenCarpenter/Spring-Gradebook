package com.school.gradebook.controller;

import com.school.gradebook.controller.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/semester")
public class SemesterController {
    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @RequestMapping
    public String semester(Model model) {
        model.addAttribute("semesters", semesterService.getSemesters());
        return "Semester";
    }
}
