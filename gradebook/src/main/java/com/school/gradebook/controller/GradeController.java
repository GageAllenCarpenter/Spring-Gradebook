package com.school.gradebook.controller;

import com.school.gradebook.controller.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/grade")
public class GradeController {
    private final GradeService gradeService;
    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    @RequestMapping
    public String grade(Model model) {
        model.addAttribute("grades", gradeService.getGrades());
        return "Grade";
    }
}
