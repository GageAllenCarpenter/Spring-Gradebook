package com.school.gradebook.controller;

import com.school.gradebook.controller.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @RequestMapping
    public String assignment(Model model) {
        model.addAttribute("assignments", assignmentService.getAssignments());
        return "Assignment";
    }
}
