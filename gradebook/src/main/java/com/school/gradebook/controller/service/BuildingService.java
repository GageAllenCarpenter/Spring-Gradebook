package com.school.gradebook.controller.service;

import com.school.gradebook.controller.repository.BuildingRepository;
import com.school.gradebook.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(Long id) {
        return buildingRepository.findById(id).orElse(null);
    }

    public void addBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void updateBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
