package com.projects.salon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryInfo {
    private String name;
    private int year;
    private int month;
    private int total;
    private int salary;
}
