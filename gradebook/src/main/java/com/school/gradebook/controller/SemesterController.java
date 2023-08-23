package com.school.gradebook.controller;

import com.school.gradebook.controller.service.SemesterService;
import com.school.gradebook.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/semester")
public class SemesterController {
    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping
    public String showSemestersForm(Model model) {
        model.addAttribute("semesters", semesterService.getSemesters());
        return "Semester/Semester";
    }

    @GetMapping("/add")
    public String showAddSemesterForm(Model model) {
        model.addAttribute("semester", new Semester());
        return "Semester/AddSemester";
    }

    @PostMapping("/add")
    public String addSemester(@ModelAttribute Semester semester) {
        semesterService.addSemester(semester);
        return "redirect:/semester";
    }

    @GetMapping("/update")
    public String showUpdateSemesterForm() {
        return "Semester/UpdateSemester";
    }

    @GetMapping("/update/{id}")
    public String showUpdateSemesterForm(@PathVariable Long id, Model model) {
        Semester semester = semesterService.getSemesterById(id);
        model.addAttribute("semester", semester);
        return "Semester/UpdateSemester";
    }

    @PostMapping("/update/{id}")
    public String updateSemester(@PathVariable Long id, @ModelAttribute Semester updatedSemester) {
        Semester existingSemester = semesterService.getSemesterById(id);

        if (existingSemester != null) {
            existingSemester.setStartDate(updatedSemester.getStartDate());
            existingSemester.setEndDate(updatedSemester.getEndDate());
            semesterService.updateSemester(existingSemester);
        }

        return "redirect:/semester";
    }

    @GetMapping("/delete")
    public String showDeleteSemesterForm() {
        return "Semester/DeleteSemester";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteSemesterForm(@PathVariable Long id, Model model) {
        Semester semester = semesterService.getSemesterById(id);
        if (semester == null) return "redirect:/semester";
        model.addAttribute("semester", semester);
        return "Semester/DeleteSemester";
    }

    @PostMapping("/delete/{id}")
    public String deleteSemester(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return "redirect:/semester";
    }
}
