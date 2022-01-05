package com.info.competition.controller;

import com.info.competition.dto.LoginDto;
import com.info.competition.dto.UserDto;
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

//    @PostMapping("/login")
//    public String login(LoginDto loginDto, HttpSession session, Model model) {
//        String msg = "";
//        if (loginDto.getUsername() == null || loginDto.getUsername() == "") {
//            msg = "请输入用户名";
//        }
//        else if (loginDto.getPassword() == null || loginDto.getPassword() == "") {
//            msg = "请输入密码";
//        }
//        else {
//            Integer userId = userService.login(loginDto);
//            if (userId == -1) {
//                msg = "用户名或密码或类型错误";
//            }
//            else {
//                session.setAttribute("userId", userId);
//                return "redirect:/index";
//            }
//        }
//        model.addAttribute("msg", msg);
//        return "login";
//    }
//
//    @RequestMapping("/index")
//    public String index(HttpSession session, Model model) {
//        Integer userId = (Integer)session.getAttribute("userId");
//        User thisUser = userService.getUserById(userId);
//        model.addAttribute("thisUser",thisUser);
//        return "index";
//    }

//    @RequestMapping("/home")
//    public String toHome() {
//        return "home";
//    }

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
                return "home";
            }
        }
        model.addAttribute("msg", msg);
        return "login";
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
