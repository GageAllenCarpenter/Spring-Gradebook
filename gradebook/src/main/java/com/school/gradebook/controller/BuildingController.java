package com.school.gradebook.controller;

import com.school.gradebook.controller.service.BuildingService;
import com.school.gradebook.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/building")
public class BuildingController {
    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @RequestMapping
    public String showBuildingsForm(Model model) {
        model.addAttribute("buildings", buildingService.getBuildings());
        return "Building/Building";
    }

    @GetMapping("/add")
    public String showAddBuildingForm(Model model) {
        model.addAttribute("building", new Building());
        return "Building/AddBuilding";
    }

    @PostMapping("/add")
    public String addBuilding(@ModelAttribute Building building) {
        buildingService.addBuilding(building);
        return "redirect:/building";
    }

    @GetMapping("/update")
    public String showUpdateBuildingForm() {
        return "Building/UpdateBuilding";
    }

    @GetMapping("/update/{id}")
    public String showUpdateBuildingForm(@PathVariable Long id, Model model) {
        Building building = buildingService.getBuildingById(id);
        model.addAttribute("building", building);
        return "Building/UpdateBuilding";
    }

    @PostMapping("/update/{id}")
    public String updateBuilding(@PathVariable Long id, @ModelAttribute Building updatedBuilding) {
        Building existingBuilding = buildingService.getBuildingById(id);

        if (existingBuilding != null) {
            existingBuilding.setName(updatedBuilding.getName());
            existingBuilding.setCity(updatedBuilding.getCity());
            existingBuilding.setState(updatedBuilding.getState());
            existingBuilding.setZipcode(updatedBuilding.getZipcode());
            buildingService.updateBuilding(existingBuilding);
        }

        return "redirect:/building";
    }

    @GetMapping("/delete")
    public String showDeleteBuildingForm() {
        return "Building/DeleteBuilding";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteBuildingForm(@PathVariable Long id, Model model) {
        Building building = buildingService.getBuildingById(id);
        if (building == null) return "redirect:/building";
        model.addAttribute("building", building);
        return "Building/DeleteBuilding";
    }

    @PostMapping("/delete/{id}")
    public String deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return "redirect:/building";
    }
}
