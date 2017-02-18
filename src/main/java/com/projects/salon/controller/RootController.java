package com.projects.salon.controller;

import com.projects.salon.LoggedUser;
import com.projects.salon.entity.Employee;
import com.projects.salon.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.projects.salon.entity.Role.ROLE_USER;

@Controller
@RequestMapping
public class RootController {
    private final EmployeeService employeeService;

    @Autowired
    public RootController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

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

    @GetMapping("/profile")
    public String profile(Model model) {
        Employee loggedUser = LoggedUser.getUser();
        if (loggedUser.getRoles().contains(ROLE_USER)) {
            model.addAttribute("hURL", "/user");
        } else {
            model.addAttribute("hURL", "/admin");
        }
        model.addAttribute("user", loggedUser);
        return "profile";
    }

    @PostMapping("/profile")
    public String profile(@RequestParam String name,
                          @RequestParam String email,
                          @RequestParam String phone,
                          @RequestParam String password) {
        Employee loggedUser = LoggedUser.getUser();
        loggedUser.setName(name);
        loggedUser.setEmail(email);
        loggedUser.setPhone(phone);
        loggedUser.setPassword(password);
        employeeService.update(loggedUser);
        if (loggedUser.getRoles().contains(ROLE_USER)) {
            return "redirect:user";
        } else {
            return "redirect:admin";
        }
    }

    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAttribute("hURL", "/admin");
        return "employee";
    }

    @GetMapping("/clients")
    public String clients(Model model) {
        model.addAttribute("hURL", "/admin");
        return "client";
    }

    @GetMapping("/salary")
    public String salary(Model model) {
        model.addAttribute("hURL", "/admin");
        return "salary";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
