package com.school.gradebook.controller;

import com.school.gradebook.controller.service.*;
import com.school.gradebook.model.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/division")
public class DivisionController {

    private final DivisionService divisionService;
    private final ProfessorService professorService;
    private final SemesterService semesterService;
    private final CourseService courseService;
    private final RoomService roomService;

    @Autowired
    public DivisionController(
            DivisionService divisionService,
            ProfessorService professorService,
            SemesterService semesterService,
            CourseService courseService,
            RoomService roomService
    ) {
        this.divisionService = divisionService;
        this.professorService = professorService;
        this.semesterService = semesterService;
        this.courseService = courseService;
        this.roomService = roomService;
    }

    @GetMapping
    public String showDivisionsForm(Model model) {
        model.addAttribute("divisions", divisionService.getDivisions());
        return "Division/Division";
    }

    @GetMapping("/add")
    public String showAddDivisionForm(Model model) {
        model.addAttribute("division", new Division());
        model.addAttribute("professors", professorService.getProfessors());
        model.addAttribute("semesters", semesterService.getSemesters());
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("rooms", roomService.getRooms());
        return "Division/AddDivision";
    }

    @PostMapping("/add")
    public String addDivision(
            @ModelAttribute Division division,
            @RequestParam("professorId") Long professorId,
            @RequestParam("semesterId") Long semesterId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("roomId") Long roomId
    ) {
        division.setProfessor(professorService.getProfessorById(professorId));
        division.setSemester(semesterService.getSemesterById(semesterId));
        division.setCourse(courseService.getCourseById(courseId));
        division.setRoom(roomService.getRoomById(roomId));
        divisionService.addDivision(division);
        return "redirect:/division";
    }

    @GetMapping("/update")
    public String showUpdateDivisionForm(Model model) {
        model.addAttribute("professors", professorService.getProfessors());
        model.addAttribute("semesters", semesterService.getSemesters());
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("rooms", roomService.getRooms());
        return "Division/UpdateDivision";
    }

    @GetMapping("/update/{id}")
    public String showUpdateDivisionForm(@PathVariable Long id, Model model) {
        Division division = divisionService.getDivisionById(id);
        model.addAttribute("professors", professorService.getProfessors());
        model.addAttribute("semesters", semesterService.getSemesters());
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("rooms", roomService.getRooms());
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
