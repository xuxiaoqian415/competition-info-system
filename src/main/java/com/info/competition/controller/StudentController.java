package com.info.competition.controller;

import com.info.competition.model.Competition;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.model.dto.UserDto;
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
    UserService userService;
    @Autowired
    SelectService selectService;


    @GetMapping("/informList")
    public String toInformList(Model model) {
        List<Competition> competitionList = competitionService.getCompetitionByApply();
        model.addAttribute("competitionList",competitionList);
        return "admin/informList";
    }

    @GetMapping("/competitionDetail/{id}")
    public String toCompetitionDetail(@PathVariable("id") Integer id, Model model) {
        Competition detail = competitionService.getCompetitionDetail(id);
        model.addAttribute("detail",detail);
        return "admin/competitionDetail";
    }

    @GetMapping("/applyList")
    public String toApplyList() {

        return "admin/applyList";
    }

    @GetMapping("/apply/{cpId}")
    public String toApply(@PathVariable("cpId") Integer cpId, Model model){
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("cpId", cpId);
        model.addAttribute("studentList",studentList);
        return "admin/apply";
    }

    @PostMapping("/apply")
    public String apply(TeamDto teamDto, HttpSession session, Model model){
        String msg = "";
        StringBuffer memberBuffer = new StringBuffer();
        if(teamDto.getMember1Id() != null){
            memberBuffer.append(teamDto.getMember1Id() + ";");
        }
        if(teamDto.getMember2Id() != null){
            memberBuffer.append(teamDto.getMember2Id() + ";");
        }
        if(teamDto.getMember3Id() != null){
            memberBuffer.append(teamDto.getMember3Id() + ";");
        }
        if(teamDto.getMember4Id() != null){
            memberBuffer.append(teamDto.getMember4Id() + ";");
        }
        String member = memberBuffer.toString();
        teamDto.setLeaderId(((UserDto)session.getAttribute("thisUser")).getId());
        teamDto.setMember(member);
        Integer teamId = teamService.buildTeam(teamDto);
        if(-1 == teamId){
            msg = "报名失败";
            model.addAttribute("msg",msg);
            return toApply(teamDto.getCpId(), model);
        }
        else{
            session.setAttribute("teamId",teamId);
            return "student/chooseTeacher";
        }
    }

    @GetMapping("/choose")
    public String toChoose(HttpSession session){
        List<UserDto> teacherList = userService.getAllTeacher();
        session.setAttribute("teacherList",teacherList);
        return "student/chooseTeacher";
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
        return "student/chooseTeacher";
    }

}
