package com.info.competition.controller;

import com.info.competition.model.Competition;
import com.info.competition.model.Query;
import com.info.competition.model.dto.CompetitionDto;
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

    @GetMapping("/competitionList")
    public String toCompetitionList(Model model) {
        List<Competition> competitionList = competitionService.getCompetitionList();
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    @PostMapping("searchCompetition")
    public String searchCompetition(Query query, Model model) {
        List<Competition> competitionList = competitionService.searchCompetition(query);
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    @GetMapping("/deleteCompetition/{Id}")
    public String deleteCompetition(@PathVariable("Id") Integer id, Model model){
        String msg = "";
        if(-1 == competitionService.deleteCompetition(id)){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toCompetitionList(model);
    }

    @GetMapping("/userList")
    public String toUserList(Model model) {
        List<UserDto> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        return "admin/userList";
    }

    @PostMapping("/searchUser")
    public String searchUser(Query query, Model model) {
        List<UserDto> userList = userService.searchUser(query);
        model.addAttribute("userList",userList);
        return "admin/userList";
    }

    @GetMapping("/deleteUser/{Id}")
    public String deleteUser(@PathVariable("Id") Integer id, Model model){
        String msg = "";
        if(-1 == userService.deleteUser(id)){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toUserList(model);
    }

    @GetMapping("/updateUser/{userId}")
    public String toUpdateUser(@PathVariable("userId") Integer userId, Model model){
        UserDto thisUser = userService.getUserById(userId);
        model.addAttribute("userInfo",thisUser);
        return "admin/adminUpdateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        Integer flag = userService.updateUser(userDto);
        if(-1 == flag){
            msg = "修改信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateUser(userDto.getId(),model);
        }
        msg = "修改信息成功!";
        UserDto thisUser = userService.getUserById(userDto.getId());
        session.setAttribute("thisUser", thisUser);
        model.addAttribute("msg", msg);
        return toUpdateUser(userDto.getId(),model);
    }

    @GetMapping("/teamList")
    public String toTeamList(Model model) {
        List<TeamDto> teamList = teamService.getAllTeam();
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    @PostMapping("/searchTeam")
    public String searchTeam(Query query, Model model) {
        List<TeamDto> teamList = teamService.searchTeam(query);
        model.addAttribute("teamList",teamList);
        return "admin/teamList";
    }

    @GetMapping("/deleteTeam/{Id}")
    public String deleteTeam(@PathVariable("Id") Integer id,Model model){
        String msg = "";
        if(-1 == teamService.deleteTeam(id)){
            msg = "删除失败!";
            model.addAttribute("msg", msg);
        }
        else{
            msg = "删除成功!";
            model.addAttribute("msg", msg);
        }
        return toTeamList(model);
    }

    @GetMapping("/updateCompetition/{Id}")
    public String toUpdateCompetition(@PathVariable("Id") Integer id,Model model){
        CompetitionDto competition = competitionService.getCompetitionDetail(id);
        model.addAttribute("thisCompetition",competition);
        return "admin/updateCompetition";
    }

    @PostMapping("/updateCompetition")
    public String updateCompetition(CompetitionDto dto, HttpSession session, Model model){
        String msg = "";
        if(competitionService.updateCompetition(dto) != 1) {
            msg = "修改竞赛信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateCompetition(dto.getId(),model);
        }
        msg = "修改竞赛信息成功!";
        model.addAttribute("msg", msg);
        return toUpdateCompetition(dto.getId(),model);
    }

    @GetMapping("/updateTeam/{Id}")
    public String toUpdateTeam(@PathVariable("Id") Integer id,Model model){
        TeamDto teamDto = teamService.getTeamById(id);
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("thisTeam",teamDto);
        model.addAttribute("studentList",studentList);
        return "admin/adminUpdateTeam";
    }

    @PostMapping("/updateTeam")
    public String updateTeam(TeamDto dto, Model model){
        String msg = "";
        if(teamService.adminUpdateTeam(dto)!= 1){
            msg = "修改团队信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateTeam(dto.getId(),model);
        }
        msg = "修改团队信息成功!";
        model.addAttribute("msg", msg);
        return toUpdateTeam(dto.getId(),model);
    }

    @GetMapping("/addUser")
    public String toAddUser(){
        return "admin/addUser";
    }

    @PostMapping("/addUser")
    public String addUser(UserDto userDto,Model model){
        String msg = "";
        if(!userDto.getNowpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return "admin/addUser";
        }
        userService.addUser(userDto);
        msg = "添加学生成功!";
        model.addAttribute("msg", msg);
        return "admin/addUser";
    }

}
