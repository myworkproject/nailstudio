package com.projects.salon.controller;

import com.projects.salon.entity.SalaryInfo;
import com.projects.salon.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryRestController {

    private final SalaryService salaryService;

    @Autowired
    public SalaryRestController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/all/{month}")
    public List<SalaryInfo> getAllInfos(@PathVariable int month) {
        return salaryService.getAllInfos(month);
    }
}
