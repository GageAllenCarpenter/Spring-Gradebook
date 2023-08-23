package com.school.gradebook.controller;

import com.school.gradebook.controller.service.ProfessorService;
import com.school.gradebook.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;
    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public String showProfessorsForm(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        return "Professor/Professor";
    }

    @GetMapping("/add")
    public String showAddProfessorForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "Professor/AddProfessor";
    }

    @PostMapping("/add")
    public String addProfessor(@ModelAttribute Professor professor) {
        professorService.addProfessor(professor);
        return "redirect:/professor";
    }

    @GetMapping("/update")
    public String showUpdateProfessorForm() {
        return "Professor/UpdateProfessor";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProfessorForm(@PathVariable Long id, Model model) {
        Professor professor = professorService.getProfessorById(id);
        model.addAttribute("professor", professor);
        return "Professor/UpdateProfessor";
    }

    @PostMapping("/update/{id}")
    public String updateProfessor(@PathVariable Long id, @ModelAttribute Professor updatedProfessor) {
        Professor existingProfessor = professorService.getProfessorById(id);

        if (existingProfessor != null) {
            existingProfessor.setFirstName(updatedProfessor.getFirstName());
            existingProfessor.setLastName(updatedProfessor.getLastName());
            existingProfessor.setEmail(updatedProfessor.getEmail());
            existingProfessor.setPhoneNumber(updatedProfessor.getPhoneNumber());
            existingProfessor.setAddress(updatedProfessor.getAddress());
            professorService.updateProfessor(existingProfessor);
        }

        return "redirect:/professor";
    }

    @GetMapping("/delete")
    public String showDeleteProfessorForm() {
        return "Professor/DeleteProfessor";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteProfessorForm(@PathVariable Long id, Model model) {
        Professor professor = professorService.getProfessorById(id);
        if (professor == null) return "redirect:/professor";
        model.addAttribute("professor", professor);
        return "Professor/DeleteProfessor";
    }

    @PostMapping("/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return "redirect:/professor";
    }
}
