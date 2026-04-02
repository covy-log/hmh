package com.hmh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/") // 첫 화면
    public String home(Model model) {
        model.addAttribute("message", "HMH !!!");

        return "index";
    }
}
