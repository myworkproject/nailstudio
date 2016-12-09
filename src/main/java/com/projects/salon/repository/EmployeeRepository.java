package com.projects.salon.repository;

import com.projects.salon.entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll();

    Employee getById(int id);
}
