package com.school.gradebook.controller;

import com.school.gradebook.controller.service.CourseService;
import com.school.gradebook.controller.service.ProfessorService;
import com.school.gradebook.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final ProfessorService professorService;

    @Autowired
    public CourseController(
            CourseService courseService,
            ProfessorService professorService
    ) {
        this.courseService = courseService;
        this.professorService = professorService;
    }

    @GetMapping
    public String showCoursesForm(Model model) {
        model.addAttribute("courses", courseService.getCourses());
        return "Course/Course";
    }

    @GetMapping("/add")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("professors", professorService.getProfessors());
        return "Course/AddCourse";
    }

    @PostMapping("/add")
    public String addCourse(
            @ModelAttribute("course") Course course,
            @RequestParam("professorId") Long professorId
    ) {
        course.setProfessor(professorService.getProfessorById(professorId));
        courseService.addCourse(course);
        return "redirect:/course";
    }

    @GetMapping("/update")
    public String showUpdateCourseForm(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        return "Course/UpdateCourse";
    }

    @GetMapping("/update/{id}")
    public String showUpdateCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("professors", professorService.getProfessors());
        return "Course/UpdateCourse";
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course updatedCourse) {
        Course existingCourse = courseService.getCourseById(id);

        if (existingCourse != null) {
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setCreditHour(updatedCourse.getCreditHour());
            existingCourse.setProfessor(updatedCourse.getProfessor());
            courseService.updateCourse(existingCourse);
        }

        return "redirect:/course";
    }

    @GetMapping("/delete")
    public String showDeleteCourseForm() {
        return "Course/DeleteCourse";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course == null) return "redirect:/course";
        model.addAttribute("course", course);
        return "Course/DeleteCourse";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }
}
