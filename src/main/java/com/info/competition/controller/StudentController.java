package com.info.competition.controller;

import com.info.competition.model.dto.SelectDto;
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
            return "redirect:/student/choose";
        }
    }

    @GetMapping("/choose")
    public String toChoose(HttpSession session, Model model){
        List<UserDto> teacherList = userService.getTeacherList();
        Integer teamId = (Integer) session.getAttribute("teamId");
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("teamId",teamId);
        return "student/chooseTeacher";
    }

    @PostMapping("/choose")
    public String choose(SelectDto selectDto, HttpSession session, Model model){
        if (selectDto.getTeacher1Id() == null || selectDto.getTeacher2Id() == null || selectDto.getTeacher3Id() == null) {
            String msg = "请选择三个老师";
            model.addAttribute("msg", msg);
            return toChoose(session, model);
        }
        if (selectDto.getTeacher1Id() == selectDto.getTeacher2Id() ||
                selectDto.getTeacher1Id() == selectDto.getTeacher3Id() ||
                selectDto.getTeacher2Id() == selectDto.getTeacher3Id()) {
            String msg = "三个志愿不能选同样的老师";
            model.addAttribute("msg", msg);
            return toChoose(session, model);
        }
        if(selectService.insertSelect(selectDto) == -1){
            String msg = "选择教师失败";
            model.addAttribute("msg", msg);
            return toChoose(session, model);
        }
        String msg = "选择教师成功";
        model.addAttribute("msg", msg);
        return toChoose(session, model);
    }

    @GetMapping("/updateUser")
    public String toUpdateUser(HttpSession session, Model model){
        return "student/updateUser";
    }

    @PostMapping("/updateUser")
    public String UpdateUser(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        String number = ((UserDto) session.getAttribute("thisUser")).getNumber();
        userDto.setNumber(number);
        Integer userId = userService.updateUser(userDto);
        if(-1 == userId){
            msg = "修改信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateUser(session,model);  //返回个人信息页面?
        }
        else{
            msg = "修改信息成功!";
            UserDto thisUser = userService.getUserById(userId);
            session.setAttribute("thisUser", thisUser);
            model.addAttribute("msg", msg);
            return toUpdateUser(session,model);  //返回个人信息页面?
        }
    }


    @GetMapping("/updatePsw")
    public String toUpdatePsw(HttpSession session, Model model){
        return "student/updateUser";
    }

    @PostMapping("/updatePsw")
    public String UpdatePsw(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        String number = ((UserDto) session.getAttribute("thisUser")).getNumber();
        userDto.setNumber(number);
        if(!userDto.getNewpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(session,model); //返回修改密码页面
        }
        else {
            Integer userId = userService.updatePsw(userDto);
            if(-1 == userId){
                msg = "修改密码失败!";
                model.addAttribute("msg", msg);
                return toUpdatePsw(session,model);  //返回修改密码页面

            }
            else if (-2 == userId){
                msg = "原密码错误!";
                model.addAttribute("msg", msg);
                return toUpdatePsw(session,model);  //返回修改密码页面
            }
            else{
                msg = "修改信息成功!";
                UserDto thisUser = userService.getUserById(userId);
                session.setAttribute("thisUser", thisUser);
                model.addAttribute("msg", msg);
                return "/login";  //返回登录页面
            }
        }
    }

}
