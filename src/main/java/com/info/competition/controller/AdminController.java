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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CompetitionService competitionService;

    @GetMapping("/postCompetition")
    public String toPostCompetition() {

        return "postCompetition";
    }

    @GetMapping("/accountList")
    public String toAccountList() {

        return "accountList";
    }

    @GetMapping("/competitionList")
    public String toCompetitionList(Model model) {
        List<Competition> competitionList = competitionService.getCompetitionList();
        model.addAttribute("competitionList", competitionList);
        return "competitionList";
    }

    @GetMapping("/teamList")
    public String toTeamList() {

        return "teamList";
    }
}
