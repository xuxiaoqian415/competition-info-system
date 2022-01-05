package com.info.competition.controller;

import com.info.competition.model.Competition;
import com.info.competition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/postCompetition")
    public String postCompetition(Competition competition, Model model){
        int i=competitionService.addCompetition(competition);
        if(i==0) model.addAttribute("msg","发布失败");
        else model.addAttribute("msg","发布成功");
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
