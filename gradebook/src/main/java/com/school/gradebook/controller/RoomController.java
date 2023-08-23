package com.school.gradebook.controller;

import com.school.gradebook.controller.service.RoomService;
import com.school.gradebook.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String showRoomsForm(Model model) {
        model.addAttribute("rooms", roomService.getRooms());
        return "Room/Room";
    }

    @GetMapping("/add")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "Room/AddRoom";
    }

    @PostMapping("/add")
    public String addRoom(@ModelAttribute Room room) {
        roomService.addRoom(room);
        return "redirect:/room";
    }

    @GetMapping("/update")
    public String showUpdateRoomForm() {
        return "Room/UpdateRoom";
    }

    @GetMapping("/update/{id}")
    public String showUpdateRoomForm(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "Room/UpdateRoom";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room updatedRoom) {
        Room existingRoom = roomService.getRoomById(id);

        if (existingRoom != null) {
            existingRoom.setBuilding(updatedRoom.getBuilding());
            roomService.updateRoom(existingRoom);
        }

        return "redirect:/room";
    }

    @GetMapping("/delete")
    public String showDeleteRoomForm() {
        return "Room/DeleteRoom";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteRoomForm(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id);
        if (room == null) return "redirect:/room";
        model.addAttribute("room", room);
        return "Room/DeleteRoom";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/room";
    }
}
