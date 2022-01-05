package com.info.competition.controller;

import com.info.competition.dto.TeamDto;
import com.info.competition.dto.UserDto;
import com.info.competition.model.Competition;
import com.info.competition.service.CompetitionService;
import com.info.competition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    CompetitionService competitionService;
    @Autowired
    TeamService teamService;

    @GetMapping("/informList")
    public String toInformList() {

        return "informList";
    }

    @GetMapping("/applyList")
    public String toApplyList() {

        return "applyList";
    }

    @GetMapping("/competitionList")
    public String toCpList(){
        return  "competitionList";
    }

    @RequestMapping("/competitionList")
    public String competitionList(HttpSession session){
        List<Competition> competitionList = competitionService.getCompetitionByApply();
        session.setAttribute("competitionList",competitionList);
        return "competitionList";
    }

    @GetMapping("/apply")
    public String toApply(){
        return "apply";
    }

    @PostMapping("/apply/{cpId}")
    public String apply(@PathVariable("cpId")Integer cpId, String teamName, String teamIntro, Integer member1Id,
                        Integer member2Id, Integer member3Id, Integer member4Id, HttpSession session, Model model){
        String msg = "";
        StringBuffer memberBuffer = new StringBuffer();
        if(member1Id != null){
            memberBuffer.append(member1Id);
        }
        if(member2Id != null){
            memberBuffer.append(member2Id);
        }
        if(member3Id != null){
            memberBuffer.append(member3Id);
        }
        if(member4Id != null){
            memberBuffer.append(member4Id);
        }
        String member = memberBuffer.toString();
        TeamDto teamDto = new TeamDto();
        teamDto.setCpId(cpId);
        teamDto.setTeamName(teamName);
        teamDto.setLeaderId(((UserDto)session.getAttribute("thisUser")).getId());
        teamDto.setTeamIntro(teamIntro);
        teamDto.setMember(member);
        if(1 == teamService.buildTeam(teamDto)){
            return "choose";
        }
        else{
            msg = "报名失败";
        }
        model.addAttribute("msg",msg);
        return "apply";
    }

    @GetMapping("/choose")
    public String toChoose(){
        return "choose";
    }

}
