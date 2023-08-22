package com.school.gradebook.controller;

import com.school.gradebook.controller.repository.DivisionRepository;
import com.school.gradebook.controller.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/division")
public class DivisionController {

    private final DivisionService divisionService;

    @Autowired
    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }
    @RequestMapping
    public String division(Model model) {
        model.addAttribute("divisions", divisionService.getDivisions());
        return "Division";
    }
}
