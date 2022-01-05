package com.info.competition.controller;

import com.info.competition.model.LoginDto;
import com.info.competition.model.User;
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
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginDto loginDto, HttpSession session, Model model) {
        String msg = "";
        if (loginDto.getUsername() == null || loginDto.getUsername() == "") {
            msg = "请输入用户名";
        }
        else if (loginDto.getPassword() == null || loginDto.getPassword() == "") {
            msg = "请输入密码";
        }
        else {
            Integer userId = userService.login(loginDto);
            if (userId == -1) {
                msg = "用户名或密码或类型错误";
            }
            else {
                session.setAttribute("userId", userId);
                return "redirect:/index";
            }
        }
        model.addAttribute("msg", msg);
        return "login";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        Integer userId = (Integer)session.getAttribute("userId");
        User thisUser = userService.getUserById(userId);
        model.addAttribute("thisUser",thisUser);
        return "index";
    }

    @RequestMapping("/home")
    public String toHome() {
        return "home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @RequestMapping("/test")
    public void test() {
        System.out.println("test");
    }
}
