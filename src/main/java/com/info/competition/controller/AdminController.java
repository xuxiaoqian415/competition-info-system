package com.info.competition.controller;

import com.info.competition.model.Competition;
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

    @GetMapping("/userList")
    public String toUserList(Model model) {
        List<UserDto> userList = userService.getAllUser();
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

    @GetMapping("/teamList")
    public String toTeamList(Model model) {
        List<TeamDto> teamList = teamService.getAllTeam();
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
    public String toUpdateCompetition(@PathVariable("Id") Integer id,Model model,HttpSession session){
        Competition competition = competitionService.getCompetitionDetail(id);
        session.setAttribute("thisCompetition",competition);
        return "admin/updateCompetition";
    }

    @PostMapping("/updateCompetition")
    public String updateCompetition(CompetitionDto competitionDto, HttpSession session, Model model){
        String msg = "";
        Integer id = ((Competition)session.getAttribute("thisCompetition")).getId();
        competitionDto.setId(id);
        if(competitionService.updateCompetition(competitionDto) != 1){
            msg = "修改竞赛信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateCompetition(id,model,session);
        }
        else{
            msg = "修改竞赛信息成功!";
            model.addAttribute("msg", msg);
            return toCompetitionList(model);
        }
    }

    @GetMapping("/updateTeam/{Id}")
    public String toUpdateTeam(@PathVariable("Id") Integer id,Model model,HttpSession session){
        TeamDto teamDto = teamService.getTeamById(id);
        session.setAttribute("thisTeam",teamDto);
        return "admin/updateCompetition";
    }

    @PostMapping("/updateTeam")
    public String updateTeam(TeamDto teamDto, HttpSession session, Model model){
        String msg = "";
        Integer id = ((TeamDto)session.getAttribute("thisTeam")).getId();
        teamDto.setId(id);
        if(teamService.updateTeam(teamDto)!= 1){
            msg = "修改团队信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateTeam(id,model,session);
        }
        else{
            msg = "修改团队信息成功!";
            model.addAttribute("msg", msg);
            return toTeamList(model);
        }
    }

    @GetMapping("/addStudent")
    public String toAddStudent(){
        return "admin/addStudent";
    }

    @GetMapping("/addTeacher")
    public String toAddTeacher(){
        return "admin/addTeacher";
    }

    @PostMapping("/addStudent")
    public String addStudent(UserDto userDto,Model model){
        String msg = "";
        if(!userDto.getNowpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return "admin/addStudent";
        }
        else{
            userService.addUser(userDto);
            msg = "添加学生成功!";
            model.addAttribute("msg", msg);
        }
        return toUserList(model);   //学生列表
    }

    @PostMapping("/addTeacher")
    public String addTeacher(UserDto userDto,Model model){
        String msg = "";
        if(!userDto.getNowpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return "admin/addTeacher";
        }
        else{
            userService.addUser(userDto);
            msg = "添加教师成功!";
            model.addAttribute("msg", msg);
        }
        return  toUserList(model);  //教师列表
    }

}
