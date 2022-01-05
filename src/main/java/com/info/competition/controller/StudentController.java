package com.info.competition.controller;

import com.info.competition.dto.SelectDto;
import com.info.competition.dto.TeamDto;
import com.info.competition.dto.UserDto;
import com.info.competition.model.Competition;
import com.info.competition.service.CompetitionService;
import com.info.competition.service.SelectService;
import com.info.competition.service.TeamService;
import com.info.competition.service.UserService;
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
    @Autowired
    UserService userService;
    @Autowired
    SelectService selectService;


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
        Integer teamId = teamService.buildTeam(teamDto);
        if(-1 == teamId){
            msg = "报名失败";
            model.addAttribute("msg",msg);
            return "apply";
        }
        else{
            session.setAttribute("teamId",teamId);
            return "choose";
        }

    }

    @GetMapping("/choose")
    public String toChoose(HttpSession session){
        List<UserDto> teacherList = userService.getAllTeacher();
        session.setAttribute("teacherList",teacherList);
        return "choose";
    }

    @PostMapping("/choose")
    public String choose(String teacher1Id,String teacher2Id,String teacher3Id,HttpSession session,Model model){
        Integer teamId = (Integer)session.getAttribute("teamId");
        boolean flag = true;
        if(teacher1Id != null){
            SelectDto selectDto = new SelectDto();
            selectDto.setTeamId(teamId);
            selectDto.setTeacherId(Integer.parseInt(teacher1Id));
            if(selectService.insertSelect(selectDto) !=1){
                flag = false;
            }
        }
        if(teacher2Id != null){
            SelectDto selectDto = new SelectDto();
            selectDto.setTeamId(teamId);
            selectDto.setTeacherId(Integer.parseInt(teacher2Id));
            if(selectService.insertSelect(selectDto) !=1){
                flag = false;
            }
        }
        if(teacher3Id != null){
            SelectDto selectDto = new SelectDto();
            selectDto.setTeamId(teamId);
            selectDto.setTeacherId(Integer.parseInt(teacher3Id));
            if(selectService.insertSelect(selectDto) !=1){
                flag = false;
            }
        }
        if(flag){
            return "competitionList";
        }
        String msg = "选择教师失败。";
        model.addAttribute(msg);
        return "choose";
    }

}
