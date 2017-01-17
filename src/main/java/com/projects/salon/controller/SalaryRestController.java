package com.projects.salon.controller;

import com.projects.salon.entity.SalaryInfo;
import com.projects.salon.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryRestController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/all")
    public List<SalaryInfo> getAllInfos() {
        return salaryService.getAllInfos();
    }
}
