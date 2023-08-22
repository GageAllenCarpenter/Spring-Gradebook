package com.school.gradebook.controller;

import com.school.gradebook.controller.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @RequestMapping
    public String room(Model model) {
        model.addAttribute("rooms", roomService.getRooms());
        return "Room";
    }
}
