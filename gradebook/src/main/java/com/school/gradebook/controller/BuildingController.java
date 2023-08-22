package com.school.gradebook.controller;

import com.school.gradebook.controller.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/building")
public class BuildingController {
    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @RequestMapping
    public String assignment(Model model) {
        model.addAttribute("buildings", buildingService.getBuildings());
        return "Building";
    }
}
