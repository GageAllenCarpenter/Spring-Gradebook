package com.school.gradebook.controller;

import com.school.gradebook.controller.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @RequestMapping
    public String enrollment(Model model) {
        model.addAttribute("enrollments", enrollmentService.getEnrollments());
        return "Enrollment";
    }
}
