package com.school.gradebook.controller;

import com.school.gradebook.controller.service.StudentService;
import com.school.gradebook.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String showStudentsForm(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "Student/Student";
    }

    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "Student/AddStudent";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/student";
    }

    @GetMapping("/update")
    public String showUpdateStudentForm() {
        return "Student/UpdateStudent";
    }

    @GetMapping("/update/{id}")
    public String showUpdateStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "Student/UpdateStudent";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student updatedStudent) {
        Student existingStudent = studentService.getStudentById(id);

        if (existingStudent != null) {
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
            existingStudent.setAddress(updatedStudent.getAddress());
            studentService.updateStudent(existingStudent);
        }

        return "redirect:/student";
    }

    @GetMapping("/delete")
    public String showDeleteStudentForm() {
        return "Student/DeleteStudent";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) return "redirect:/student";
        model.addAttribute("student", student);
        return "Student/DeleteStudent";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}
