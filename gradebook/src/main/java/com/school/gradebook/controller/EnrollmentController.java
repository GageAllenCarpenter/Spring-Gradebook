package com.school.gradebook.controller;

import com.school.gradebook.controller.service.EnrollmentService;
import com.school.gradebook.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public String showEnrollmentsForm(Model model) {
        model.addAttribute("enrollments", enrollmentService.getEnrollments());
        return "Enrollment/Enrollment";
    }

    @GetMapping("/add")
    public String showAddEnrollmentForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        return "Enrollment/AddEnrollment";
    }

    @PostMapping("/add")
    public String addEnrollment(@ModelAttribute Enrollment enrollment) {
        enrollmentService.addEnrollment(enrollment);
        return "redirect:/enrollment";
    }

    @GetMapping("/update")
    public String showUpdateEnrollmentForm() {
        return "Enrollment/UpdateEnrollment";
    }

    @GetMapping("/update/{id}")
    public String showUpdateEnrollmentForm(@PathVariable Long id, Model model) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        model.addAttribute("enrollment", enrollment);
        return "Enrollment/UpdateEnrollment";
    }

    @PostMapping("/update/{id}")
    public String updateEnrollment(@PathVariable Long id, @ModelAttribute Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = enrollmentService.getEnrollmentById(id);

        if (existingEnrollment != null) {
            existingEnrollment.setEnrollDate(updatedEnrollment.getEnrollDate());
            existingEnrollment.setCurrentGrade(updatedEnrollment.getCurrentGrade());
            existingEnrollment.setMidtermGrade(updatedEnrollment.getMidtermGrade());
            existingEnrollment.setFinalGrade(updatedEnrollment.getFinalGrade());
            existingEnrollment.setDivision(updatedEnrollment.getDivision());
            existingEnrollment.setStudent(updatedEnrollment.getStudent());
            enrollmentService.updateEnrollment(existingEnrollment);
        }

        return "redirect:/enrollment";
    }

    @GetMapping("/delete")
    public String showDeleteEnrollmentForm() {
        return "Enrollment/DeleteEnrollment";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteEnrollmentForm(@PathVariable Long id, Model model) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment == null) return "redirect:/enrollment";
        model.addAttribute("enrollment", enrollment);
        return "Enrollment/DeleteEnrollment";
    }

    @PostMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return "redirect:/enrollment";
    }
}
