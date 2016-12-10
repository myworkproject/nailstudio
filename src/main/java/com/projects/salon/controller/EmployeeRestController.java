package com.projects.salon.controller;

import com.projects.salon.entity.Employee;
import com.projects.salon.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Employee> getAll() {
        LOGGER.debug("Returns all employees.");
        return employeeRepository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Employee getById(@PathVariable int id) {
        LOGGER.debug("Returns employee: {}.", id);
        return employeeRepository.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        LOGGER.debug("Delete employee: {}.", id);
        employeeRepository.delete(id);
    }

    @PostMapping
    public int saveAndReturnKey(Employee employee) {
        employee.setId(null);
        LOGGER.debug("Save employee: {}.", employee);
        return employeeRepository.save(employee);
    }

    @PutMapping
    public void update(Employee employee) {
        LOGGER.debug("Update employee {}: name={}, phone={}, salary={}.",
                employee.getId(), employee.getName(), employee.getPhone(), employee.getSalary());
        employeeRepository.update(employee);
    }
}
