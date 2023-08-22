package com.school.gradebook.controller;

import com.school.gradebook.controller.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;
    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @RequestMapping
    public String professor(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        return "Professor";
    }
}
