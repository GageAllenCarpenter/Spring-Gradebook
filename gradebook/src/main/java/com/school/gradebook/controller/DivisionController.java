package com.school.gradebook.controller;

import com.school.gradebook.controller.service.DivisionService;
import com.school.gradebook.model.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/division")
public class DivisionController {

    private final DivisionService divisionService;

    @Autowired
    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @GetMapping
    public String showDivisionsForm(Model model) {
        model.addAttribute("divisions", divisionService.getDivisions());
        return "Division/Division";
    }

    @GetMapping("/add")
    public String showAddDivisionForm(Model model) {
        model.addAttribute("division", new Division());
        return "Division/AddDivision";
    }

    @PostMapping("/add")
    public String addDivision(@ModelAttribute Division division) {
        divisionService.addDivision(division);
        return "redirect:/division";
    }

    @GetMapping("/update")
    public String showUpdateDivisionForm() {
        return "Division/UpdateDivision";
    }

    @GetMapping("/update/{id}")
    public String showUpdateDivisionForm(@PathVariable Long id, Model model) {
        Division division = divisionService.getDivisionById(id);
        model.addAttribute("division", division);
        return "Division/UpdateDivision";
    }

    @PostMapping("/update/{id}")
    public String updateDivision(@PathVariable Long id, @ModelAttribute Division updatedDivision) {
        Division existingDivision = divisionService.getDivisionById(id);

        if (existingDivision != null) {
            existingDivision.setTime(updatedDivision.getTime());
            existingDivision.setProfessor(updatedDivision.getProfessor());
            existingDivision.setSemester(updatedDivision.getSemester());
            existingDivision.setCourse(updatedDivision.getCourse());
            existingDivision.setRoom(updatedDivision.getRoom());
            divisionService.updateDivision(existingDivision);
        }

        return "redirect:/division";
    }

    @GetMapping("/delete")
    public String showDeleteDivisionForm() {
        return "Division/DeleteDivision";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteDivisionForm(@PathVariable Long id, Model model) {
        Division division = divisionService.getDivisionById(id);
        if (division == null) return "redirect:/division";
        model.addAttribute("division", division);
        return "Division/DeleteDivision";
    }

    @PostMapping("/delete/{id}")
    public String deleteDivision(@PathVariable Long id) {
        divisionService.deleteDivision(id);
        return "redirect:/division";
    }
}
