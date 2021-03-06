package com.info.competition.controller;

import com.info.competition.model.dto.LoginDto;
import com.info.competition.model.dto.UserDto;
import com.info.competition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String toLogin() {
        return "admin/login";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        UserDto thisUser = (UserDto)session.getAttribute("thisUser");
        model.addAttribute("thisUser",thisUser);
        return "admin/index";
    }

    @RequestMapping("/home")
    public String toHome() {
        return "admin/home";
    }

    @PostMapping("/login")
    public String login(LoginDto loginDto, HttpSession session, Model model) {
        String msg = "";
        if (loginDto.getNumber() == null || loginDto.getNumber() == "") {
            msg = "请输入学号/工号";
        }
        else if (loginDto.getPassword() == null || loginDto.getPassword() == "") {
            msg = "请输入密码";
        }
        else {
            Integer userId = userService.login(loginDto);
            if (-1 == userId) {
                msg = "类型错误";
            }
            else if (-2 ==userId) {
                msg = "密码错误，请重新输入";
            }
            else if (-3 ==userId) {
                msg = "无该用户，请注册";
            }
            else {
                UserDto thisUser = userService.getUserById(userId);
                session.setAttribute("thisUser", thisUser);
                return "redirect:/index";
            }
        }
        model.addAttribute("msg", msg);
        return "admin/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "admin/login";
    }

}
