package com.info.competition.controller;

import com.info.competition.model.Competition;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.model.dto.UserDto;
import com.info.competition.service.CompetitionService;
import com.info.competition.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    CompetitionService competitionService;
    @Autowired
    SelectService selectService;

    @GetMapping("/requestList")
    public String toRequestList(HttpSession session, Model model) {
        UserDto u = (UserDto) session.getAttribute("thisUser");
        List<TeamDto> noSelectTeams = selectService.noSelectTeams(u.getId());
        model.addAttribute("noSelectTeams", noSelectTeams);
        return "admin/requestList";
    }

    @GetMapping("/select")
    public String selectTeam(@RequestParam("id") Integer id, Model model, HttpSession session){
        selectService.updateSelect(id);
        return toRequestList(session, model);
    }

    @GetMapping("/agreeList")
    public String agreeTeam( Model model, HttpSession session){
        UserDto u=(UserDto) session.getAttribute("thisUser");
        List<TeamDto> teamDtos=selectService.selectTeams(u.getId());
        model.addAttribute("teamDtos",teamDtos);
        return "admin/agreeList";
    }
}
