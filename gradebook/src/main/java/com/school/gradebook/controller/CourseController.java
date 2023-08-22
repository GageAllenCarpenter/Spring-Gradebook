package com.school.gradebook.controller;

import com.school.gradebook.controller.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @RequestMapping
    public String course(Model model) {
        model.addAttribute("courses", courseService.getCourses());
        return "Course";
    }
}
