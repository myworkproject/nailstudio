package com.projects.salon.repository;

import com.projects.salon.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Employee.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Employee> getAll() {
        return jdbcTemplate.query("SELECT id,name,phone,email,salary,percent FROM employees", EMPLOYEE_ROW_MAPPER);
    }

    @Override
    public List<Employee> getAllWithoutAdmin() {
        return jdbcTemplate.query("SELECT id,name,phone,email,salary,percent FROM employees WHERE admin=FALSE", EMPLOYEE_ROW_MAPPER);
    }

    @Override
    public Employee getById(int id) {
        return jdbcTemplate.queryForObject("SELECT id,name,phone,email,salary,percent FROM employees WHERE id=?", EMPLOYEE_ROW_MAPPER, id);
    }

    @Override
    public int getEmployeeIdForClient(int clientId) {
        final int[] max = {0};
        final int[] employeeId = {0};
        jdbcTemplate.query("SELECT count(*) AS visits,employee_id\n" +
                "               FROM events\n" +
                "               WHERE client_id = ?\n" +
                "               GROUP BY employee_id", resultSet -> {
            int visits = resultSet.getInt("visits");
            if (visits > max[0]) {
                max[0] = visits;
                employeeId[0] = resultSet.getInt("employee_id");
            }
        }, clientId);
        return employeeId[0];
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM employees WHERE id=?", id);
    }

    @Override
    public int save(Employee employee) {
        jdbcTemplate.update("INSERT INTO employees(name, phone,salary,percent,email) VALUES (?,?,?,?,?)",
                employee.getName(), employee.getPhone(), employee.getSalary(), employee.getPercent(), employee.getEmail());
        return 1;
    }

    @Override
    public void update(Employee em) {
        jdbcTemplate.update("UPDATE employees SET name=?,phone=?,salary=?,percent=?,email=? WHERE id=?",
                em.getName(), em.getPhone(), em.getSalary(), em.getPercent(), em.getEmail(), em.getId());
    }
}
