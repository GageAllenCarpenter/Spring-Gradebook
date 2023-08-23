package com.school.gradebook.controller;

import com.school.gradebook.controller.service.GradeService;
import com.school.gradebook.model.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grade")
public class GradeController {
    private final GradeService gradeService;
    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public String showGradesForm(Model model) {
        model.addAttribute("grades", gradeService.getGrades());
        return "Grade/Grade";
    }

    @GetMapping("/add")
    public String showAddGradeForm(Model model) {
        model.addAttribute("grade", new Grade());
        return "Grade/AddGrade";
    }

    @PostMapping("/add")
    public String addGrade(@ModelAttribute Grade grade) {
        gradeService.addGrade(grade);
        return "redirect:/grade";
    }

    @GetMapping("/update")
    public String showUpdateGradeForm() {
        return "Grade/UpdateGrade";
    }

    @GetMapping("/update/{id}")
    public String showUpdateGradeForm(@PathVariable Long id, Model model) {
        Grade grade = gradeService.getGradeById(id);
        model.addAttribute("grade", grade);
        return "Grade/UpdateGrade";
    }

    @PostMapping("/update/{id}")
    public String updateGrade(@PathVariable Long id, @ModelAttribute Grade updatedGrade) {
        Grade existingGrade = gradeService.getGradeById(id);

        if (existingGrade != null) {
            existingGrade.setPoints(updatedGrade.getPoints());
            existingGrade.setAssignment(updatedGrade.getAssignment());
            existingGrade.setDivision(updatedGrade.getDivision());
            existingGrade.setStudent(updatedGrade.getStudent());
            existingGrade.setProfessor(updatedGrade.getProfessor());
            gradeService.updateGrade(existingGrade);
        }

        return "redirect:/grade";
    }

    @GetMapping("/delete")
    public String showDeleteGradeForm() {
        return "Grade/DeleteGrade";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteGradeForm(@PathVariable Long id, Model model) {
        Grade grade = gradeService.getGradeById(id);
        if (grade == null) return "redirect:/grade";
        model.addAttribute("grade", grade);
        return "Grade/DeleteGrade";
    }

    @PostMapping("/delete/{id}")
    public String deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return "redirect:/grade";
    }
}
