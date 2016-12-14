package com.projects.salon.controller;

import com.projects.salon.entity.Employee;
import com.projects.salon.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeRestController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Employee> getAll() {
        log.debug("Returns all employees.");
        return employeeRepository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Employee getById(@PathVariable int id) {
        log.debug("Returns employee: {}.", id);
        return employeeRepository.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.debug("Delete employee: {}.", id);
        employeeRepository.delete(id);
    }

    @PostMapping
    public void saveOrUpdate(Employee employee) {
        if (employee.getId() == 0) {
            employee.setId(null);
            log.debug("Save employee: {}.", employee);
            employeeRepository.save(employee);
        } else {
            log.debug("Update employee {}: name={}, phone={}, salary={}.",
                    employee.getId(), employee.getName(), employee.getPhone(), employee.getSalary());
            employeeRepository.update(employee);
        }
    }
}
