package com.projects.salon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private int salary;
    private int percent;
    private boolean enabled = true;
    private boolean admin = false;
    private Set<Role> roles;
}
