package com.info.competition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/informList")
    public String toInformList() {

        return "informList";
    }

    @GetMapping("/applyList")
    public String toApplyList() {

        return "applyList";
    }
}
