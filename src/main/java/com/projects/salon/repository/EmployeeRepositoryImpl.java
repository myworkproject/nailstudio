package com.projects.salon.repository;

import com.projects.salon.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Employee.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public EmployeeRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Employee> getAll() {
        return jdbcTemplate.query("SELECT id,name,phone,salary,percent FROM employees", EMPLOYEE_ROW_MAPPER);
    }

    @Override
    public List<Employee> getAllWithoutAdmin() {
        return jdbcTemplate.query("SELECT id,name,phone,salary,percent FROM employees WHERE admin=false", EMPLOYEE_ROW_MAPPER);
    }

    @Override
    public Employee getById(int id) {
        return jdbcTemplate.queryForObject("SELECT id,name,phone,salary,percent FROM employees WHERE id=?", EMPLOYEE_ROW_MAPPER, id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM employees WHERE id=?", id);
    }

    @Override
    public int save(Employee employee) {
        jdbcTemplate.update("INSERT INTO employees(name, phone,salary,percent) VALUES (?,?,?,?)",
                employee.getName(), employee.getPhone(), employee.getSalary(), employee.getPercent());
        return 1;
    }

    @Override
    public void update(Employee em) {
        jdbcTemplate.update("UPDATE employees SET name=?,phone=?,salary=?,percent=? WHERE id=?",
                em.getName(), em.getPhone(), em.getSalary(), em.getPercent(), em.getId());
    }
}
