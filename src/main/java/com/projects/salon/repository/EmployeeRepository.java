package com.projects.salon.repository;

import com.projects.salon.entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll();

    List<Employee> getAllWithoutAdmin();

    Employee getById(int id);

    int getEmployeeIdForClient(int clientId);

    void delete(int id);

    int save(Employee employee);

    void update(Employee employee);
}
