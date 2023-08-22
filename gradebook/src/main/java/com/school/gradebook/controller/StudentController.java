package com.school.gradebook.controller;

import com.school.gradebook.controller.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @RequestMapping
    public String student(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "Student";
    }
}
