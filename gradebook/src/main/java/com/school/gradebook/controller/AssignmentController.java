package com.school.gradebook.controller;

import com.school.gradebook.controller.service.AssignmentService;
import com.school.gradebook.model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String showAssignmentsForm(Model model) {
        model.addAttribute("assignments", assignmentService.getAssignments());
        return "Assignment/Assignment";
    }

    @GetMapping("/add")
    public String showAddAssignmentForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "Assignment/AddAssignment";
    }

    @PostMapping("/add")
    public String addAssignment(@ModelAttribute Assignment assignment) {
        assignmentService.addAssignment(assignment);
        return "redirect:/assignment";
    }

    @GetMapping("/update")
    public String showUpdateAssignmentForm() {
        return "Assignment/UpdateAssignment";
    }

    @GetMapping("/update/{id}")
    public String showUpdateAssignmentForm(@PathVariable Long id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        return "Assignment/UpdateAssignment";
    }

    @PostMapping("/update/{id}")
    public String updateAssignment(@PathVariable Long id, @ModelAttribute Assignment updatedAssignment) {
        Assignment existingAssignment = assignmentService.getAssignmentById(id);

        if (existingAssignment != null) {
            existingAssignment.setName(updatedAssignment.getName());
            existingAssignment.setType(updatedAssignment.getType());
            existingAssignment.setPointsPossible(updatedAssignment.getPointsPossible());
            assignmentService.updateAssignment(existingAssignment);
        }

        return "redirect:/assignment";
    }

    @GetMapping("/delete")
    public String showDeleteAssignmentForm() {
        return "Assignment/DeleteAssignment";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteAssignmentForm(@PathVariable Long id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        if (assignment == null) return "redirect:/assignment";
        model.addAttribute("assignment", assignment);
        return "Assignment/DeleteAssignment";
    }

    @PostMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return "redirect:/assignment";
    }
}
