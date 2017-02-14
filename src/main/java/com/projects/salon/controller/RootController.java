package com.projects.salon.controller;

import com.projects.salon.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RootController {

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("hURL", "/admin");
        return "adminCalendar";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("hURL", "/user");
        model.addAttribute("userId", LoggedUser.getId());
        return "userCalendar";
    }

    @GetMapping("/employees")
    public String employees() {
        return "employee";
    }

    @GetMapping("/clients")
    public String clients() {
        return "client";
    }

    @GetMapping("/salary")
    public String salary() {
        return "salary";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
