package com.info.competition.controller;

import com.info.competition.model.Competition;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.model.dto.UserDto;
import com.info.competition.service.CompetitionService;
import com.info.competition.service.TeamService;
import com.info.competition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    UserService userService;
    @Autowired
    TeamService teamService;

    @GetMapping("/postCompetition")
    public String toPostCompetition() {
        return "admin/postCompetition";
    }

    @PostMapping("/postCompetition")
    public String postCompetition(Competition competition, Model model){
        int i=competitionService.addCompetition(competition);
        if(i==0) model.addAttribute("msg","发布失败");
        else model.addAttribute("msg","发布成功");
        return "admin/postCompetition";
    }


    @GetMapping("/accountList")
    public String toAccountList() {

        return "admin/accountList";
    }

    @GetMapping("/competitionList")
    public String toCompetitionList(Model model) {
        List<Competition> competitionList = competitionService.getCompetitionList();
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    @GetMapping("/studentList")
    public String toStudentList(Model model) {
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("studentList",studentList);
        return "admin/studentList";
    }

    @GetMapping("/teacherList")
    public String toTeacherList(Model model) {
        List<UserDto> teacherList = userService.getTeacherList();
        model.addAttribute("teacherList",teacherList);
        return "admin/teacherList";
    }

    @PostMapping("/deleteUser/{Id}")
    public String DeleteUser(@PathVariable("Id") Integer id, Model model){
        String msg = "";
        if(userService.deleteUser(id) !=1){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toStudentList(model);
    }

    @GetMapping("/teamList")
    public String toTeamList(Model model) {
        List<TeamDto> teamList = teamService.getAllTeam();
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    @PostMapping("/deleteTeam/{Id}")
    public String DeleteTeam(@PathVariable("Id") Integer id,Model model){
        String msg = "";
        if(teamService.deleteTeam(id) !=1){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toTeamList(model);
    }
}
