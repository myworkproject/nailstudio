package com.projects.salon.repository;

import com.projects.salon.LoggedUser;
import com.projects.salon.entity.Employee;
import com.projects.salon.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository, UserDetailsService {
    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Employee.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> all = jdbcTemplate.query("SELECT id,name,phone,email,salary,percent,password,enabled FROM employees", EMPLOYEE_ROW_MAPPER);
        class EmployeeRole {
            final private Role role;
            final private int userId;

            private EmployeeRole(Role role, int userId) {
                this.role = role;
                this.userId = userId;
            }

            private Role getRole() {
                return role;
            }

            private int getUserId() {
                return userId;
            }
        }

        Map<Integer, List<EmployeeRole>> userRoles = jdbcTemplate.query("SELECT role, user_id FROM user_roles",
                (rs, rowNum) -> new EmployeeRole(Role.valueOf(rs.getString("role")), rs.getInt("user_id")))
                .stream().collect(Collectors.groupingBy(EmployeeRole::getUserId));

        all.forEach(u -> u.setRoles(userRoles.get(u.getId()).stream().map(EmployeeRole::getRole).collect(Collectors.toSet())));
        return all;
    }

    @Override
    public List<Employee> getAllWithoutAdmin() {
        List<Employee> all = jdbcTemplate.query("SELECT id,name,phone,email,salary,percent,password,enabled FROM employees WHERE admin=FALSE", EMPLOYEE_ROW_MAPPER);
        class EmployeeRole {
            final private Role role;
            final private int userId;

            private EmployeeRole(Role role, int userId) {
                this.role = role;
                this.userId = userId;
            }

            private Role getRole() {
                return role;
            }

            private int getUserId() {
                return userId;
            }
        }

        Map<Integer, List<EmployeeRole>> userRoles = jdbcTemplate.query("SELECT role, user_id FROM user_roles",
                (rs, rowNum) -> new EmployeeRole(Role.valueOf(rs.getString("role")), rs.getInt("user_id")))
                .stream().collect(Collectors.groupingBy(EmployeeRole::getUserId));

        all.forEach(u -> u.setRoles(userRoles.get(u.getId()).stream().map(EmployeeRole::getRole).collect(Collectors.toSet())));
        return all;
    }

    @Override
    public Employee getById(int id) {
        Employee employee = jdbcTemplate.queryForObject("SELECT id,name,phone,email,salary,percent,password,enabled FROM employees WHERE id=?", EMPLOYEE_ROW_MAPPER, id);
        return setRoles(employee);
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
        //todo roles save
        jdbcTemplate.update("INSERT INTO employees(name, phone,salary,percent,email) VALUES (?,?,?,?,?)",
                employee.getName(), employee.getPhone(), employee.getSalary(), employee.getPercent(), employee.getEmail());
        return 1;
    }

    @Override
    public void update(Employee em) {
        //todo roles save
        jdbcTemplate.update("UPDATE employees SET name=?,phone=?,salary=?,percent=?,email=? WHERE id=?",
                em.getName(), em.getPhone(), em.getSalary(), em.getPercent(), em.getEmail(), em.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = jdbcTemplate.queryForObject("SELECT id,name,phone,email,salary,percent,password,enabled FROM employees WHERE email=?", EMPLOYEE_ROW_MAPPER, s);
        return new LoggedUser(setRoles(employee));
    }

    private void insertRoles(Employee employee) {
        Set<Role> roles = employee.getRoles();
        Iterator<Role> iterator = roles.iterator();

        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, employee.getId());
                        ps.setString(2, iterator.next().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    private void deleteRoles(Employee e) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", e.getId());
    }

    private Employee setRoles(Employee e) {
        List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles  WHERE user_id=?",
                (rs, rowNum) -> Role.valueOf(rs.getString("role")), e.getId());
        e.setRoles(new HashSet<>(roles));
        return e;
    }
}
