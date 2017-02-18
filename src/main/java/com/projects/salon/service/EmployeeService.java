package com.projects.salon.service;

import com.projects.salon.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    List<Employee> getAllWithoutAdmin();

    Employee getById(int id);

    int getEmployeeIdForClient(int clientId);

    void delete(int id);

    void save(Employee employee);

    void update(Employee employee);
}
