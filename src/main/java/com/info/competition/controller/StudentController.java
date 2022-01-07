package com.info.competition.controller;

import com.info.competition.model.StuComp;
import com.info.competition.model.User;
import com.info.competition.model.dto.CompetitionDto;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    public String toCompetitionDetail(@PathVariable("id") Integer id, @RequestParam("back") String back,
                                      Model model, HttpSession session) {
        CompetitionDto detail = competitionService.getCompetitionDetail(id);
        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
        if (thisUser.getType() == 2) {
            StuComp stuComp = new StuComp();
            stuComp.setStudentId(thisUser.getId());
            stuComp.setCompetitionId(id);
            detail.setHaveApply(competitionService.ifHaveApply(stuComp));
        }
        model.addAttribute("detail",detail);
        if (back.equals("requestList")) {
            model.addAttribute("back","/teacher/requestList");
        }
        else if (back.equals("agreeList")) {
            model.addAttribute("back","/teacher/agreeList");
        }
        else if (back.equals("informList")) {
            model.addAttribute("back","/student/informList");
        }
        else if (back.equals("applyList")) {
            model.addAttribute("back","/student/applyList");
        }
        else if (back.equals("leadTeamList")) {
            model.addAttribute("back","/student/leadTeamList");
        }
        return "admin/competitionDetail";
    }

    @GetMapping("/applyList")
    public String toApplyList(HttpSession session, Model model) {
        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
        List<CompetitionDto> list = competitionService.getApplyList(thisUser.getId());
        model.addAttribute("applyList",list);
        return "admin/applyList";
    }

    @GetMapping("/apply/{cpId}")
    public String toApply(@PathVariable("cpId") Integer cpId, Model model){
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("cpId", cpId);
        model.addAttribute("studentList",studentList);
        return "admin/apply";
    }

//    @PostMapping("/apply")
//    public String apply(TeamDto teamDto, HttpSession session, Model model){
//        String msg = "";
//        ArrayList<Integer> memberList = new ArrayList<>();
//        if(teamDto.getMember1Id() != null){
//            memberList.add(teamDto.getMember1Id());
//        }
//        if(teamDto.getMember2Id() != null){
//            memberList.add(teamDto.getMember2Id());
//        }
//        if(teamDto.getMember3Id() != null){
//            memberList.add(teamDto.getMember3Id());
//        }
//        if(teamDto.getMember4Id() != null){
//            memberList.add(teamDto.getMember4Id());
//        }
//        teamDto.setMemberList(memberList);
//        teamDto.setLeaderId(((UserDto)session.getAttribute("thisUser")).getId());
//        Integer teamId = teamService.buildTeam(teamDto);
//        if(-1 == teamId){
//            msg = "报名失败";
//            model.addAttribute("msg",msg);
//            return toApply(teamDto.getCpId(), model);
//        }
//        else{
//            session.setAttribute("teamId",teamId);
//            return "redirect:/student/choose";
//        }
//    }

    @PostMapping("/apply")
    public String apply(TeamDto teamDto, HttpSession session, Model model){
        String msg = "";
        Integer id = ((UserDto)session.getAttribute("thisUser")).getId();
        ArrayList<Integer> memberList = new ArrayList<>();
        Map<Integer,Integer> memberMap = new HashMap<>();
        memberMap.put(id,id);
        if(teamDto.getMember1Id() != null){
            memberMap.put(teamDto.getMember1Id(),teamDto.getMember1Id());
        }
        if(teamDto.getMember2Id() != null){
            memberMap.put(teamDto.getMember2Id(),teamDto.getMember2Id());
        }
        if(teamDto.getMember3Id() != null){
            memberMap.put(teamDto.getMember3Id(),teamDto.getMember3Id());
        }
        if(teamDto.getMember4Id() != null){
            memberMap.put(teamDto.getMember4Id(),teamDto.getMember4Id());
        }
        memberMap.remove(id);
        Iterator<Integer> it = memberMap.keySet().iterator();
        while(it.hasNext()){
            memberList.add(memberMap.get(it.next()));
        }
        teamDto.setMemberList(memberList);
        teamDto.setLeaderId(((UserDto)session.getAttribute("thisUser")).getId());
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
        UserDto thisUser = (UserDto) session.getAttribute("thisUser");
        model.addAttribute("userInfo",thisUser);
        return "admin/updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        Integer id = ((UserDto) session.getAttribute("thisUser")).getId();
        userDto.setId(id);
        Integer flag = userService.updateUser(userDto);
        if(-1 == flag){
            msg = "修改信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateUser(session,model);
        }
        else{
            msg = "修改信息成功!";
            UserDto thisUser = userService.getUserById(id);
            session.setAttribute("thisUser", thisUser);
            model.addAttribute("msg", msg);
            return toUpdateUser(session,model);
        }
    }

    @GetMapping("/updateTeam/{Id}")
    public String toUpdateTeam(@PathVariable("Id") Integer id,Model model){
        TeamDto teamDto = teamService.getTeamById(id);
        List<UserDto> studentList = userService.getStudentList();
        model.addAttribute("thisTeam",teamDto);
        model.addAttribute("studentList",studentList);
        return "admin/updateTeam";
    }

    @PostMapping("/updateTeam")
    public String updateTeam(TeamDto dto, Model model){
        String msg = "";
        if(teamService.updateTeam(dto)!= 1){
            msg = "修改团队信息失败!";
            model.addAttribute("msg", msg);
            return toUpdateTeam(dto.getId(),model);
        }
        msg = "修改团队信息成功!";
        model.addAttribute("msg", msg);
        return toUpdateTeam(dto.getId(),model);
    }

    @GetMapping("/updatePsw")
    public String toUpdatePsw(Model model){
        return "admin/updatePsw";
    }

    @PostMapping("/updatePsw")
    public String updatePsw(UserDto userDto,HttpSession session,Model model){
        String msg = "";
        if(!userDto.getNewpsw().equals(userDto.getRpsw())) {
            msg = "两次密码不一致!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(model); //返回修改密码页面
        }
        Integer id  = ((UserDto) session.getAttribute("thisUser")).getId();
        userDto.setId(id);
        Integer flag = userService.updatePsw(userDto);
        if(-1 == flag){
            msg = "原密码错误!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(model);  //返回修改密码页面
        }
        if (-2 == flag){
            msg = "修改密码失败!";
            model.addAttribute("msg", msg);
            return toUpdatePsw(model);  //返回修改密码页面
        }
        msg = "修改密码成功，请重新登录！";
        UserDto thisUser = userService.getUserById(id);
        session.setAttribute("thisUser", thisUser);
        model.addAttribute("msg", msg);
        session.invalidate();
        return toUpdatePsw(model);  //返回修改密码页面
    }

    @GetMapping("/leadTeamList")
    public String toLeadTeamList(HttpSession session, Model model){
        UserDto u=(UserDto) session.getAttribute("thisUser");
        List<TeamDto> list = teamService.getOwnTeam(u.getId());
        model.addAttribute("leadTeamList",list);
        return "student/leadTeamList";
    }

}
