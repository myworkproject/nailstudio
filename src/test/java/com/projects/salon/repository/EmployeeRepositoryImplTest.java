package com.projects.salon.repository;

import com.projects.salon.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryImplTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void get_all() throws Exception {
        employeeRepository.getAll()
                .forEach(System.out::println);
    }

    @Test
    public void get_by_id() throws Exception {
        assertThat(employeeRepository.getById(1), notNullValue());
    }

    @Test
    public void save_new_employee() {
        Employee employee = new Employee(null, "John", "222", 200);
        int key = employeeRepository.save(employee);
        employee.setId(key);
        assertThat(employee, is(employeeRepository.getById(5)));
    }

}