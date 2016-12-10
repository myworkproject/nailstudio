package com.projects.salon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RootController {

    @GetMapping("/")
    public String home() {
        return "calendar";
    }

    @GetMapping("/employees")
    public String employees() {
        return "employee";
    }

    @GetMapping("/clients")
    public String clients() {
        return "client";
    }
}
