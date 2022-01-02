package com.info.competition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping("/informList")
    public String toInformList() {

        return "informList";
    }

    @GetMapping("/requestList")
    public String toRequestList() {

        return "requestList";
    }
}
