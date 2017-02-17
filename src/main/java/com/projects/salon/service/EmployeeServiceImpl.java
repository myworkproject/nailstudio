package com.projects.salon.service;

import com.projects.salon.entity.Employee;
import com.projects.salon.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Secured("ADMIN")
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    @Override
    @Secured("ADMIN")
    public List<Employee> getAllWithoutAdmin() {
        return employeeRepository.getAllWithoutAdmin();
    }

    @Override
    public Employee getById(int id) {
        return employeeRepository.getById(id);
    }

    @Override
    @Secured("ADMIN")
    public int getEmployeeIdForClient(int clientId) {
        return employeeRepository.getEmployeeIdForClient(clientId);
    }

    @Override
    @Secured("ADMIN")
    public void delete(int id) {
        employeeRepository.delete(id);
    }

    @Override
    @Secured("ADMIN")
    public int save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Secured("ADMIN")
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }
}
