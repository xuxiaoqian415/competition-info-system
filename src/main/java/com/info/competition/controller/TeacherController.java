package com.info.competition.controller;

import com.info.competition.model.Competition;
import com.info.competition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    CompetitionService competitionService;

    @GetMapping("/requestList")
    public String toRequestList() {

        return "admin/requestList";
    }
}
