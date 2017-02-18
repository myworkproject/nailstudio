package com.projects.salon.repository;

import com.projects.salon.LoggedUser;
import com.projects.salon.entity.Employee;
import com.projects.salon.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private static final String GET_ALL = "SELECT id,name,phone,email,salary,percent,password,enabled FROM employees";
    private static final String GET_ALL_WITHOUT_ADMIN = "SELECT id,name,phone,email,salary,percent,password,enabled FROM employees WHERE admin=FALSE";
    private static final String GET_BY_ID = "SELECT id,name,phone,email,salary,percent,password,enabled FROM employees WHERE id=?";
    private static final String GET_BY_EMAIL = "SELECT id,name,phone,email,salary,percent,password,enabled FROM employees WHERE email=?";
    private static final String GET_ALL_ROLES = "SELECT role, user_id FROM user_roles";
    private static final String GET_EMPLOYEE_ID_FOR_CLIENT = "SELECT count(*) AS visits,employee_id FROM events WHERE client_id = ? GROUP BY employee_id";
    private static final String DELETE_EMPLOYEE = "DELETE FROM employees WHERE id=?";
    private static final String UPDATE = "UPDATE employees SET name=?,phone=?,salary=?,percent=?,email=? WHERE id=?";

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Employee.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public EmployeeRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> all = jdbcTemplate.query(GET_ALL, EMPLOYEE_ROW_MAPPER);
        getAndInsertAllRoles(all);
        return all;
    }

    @Override
    public List<Employee> getAllWithoutAdmin() {
        List<Employee> all = jdbcTemplate.query(GET_ALL_WITHOUT_ADMIN, EMPLOYEE_ROW_MAPPER);
        getAndInsertAllRoles(all);
        return all;
    }

    @Override
    public Employee getById(int id) {
        Employee employee = jdbcTemplate.queryForObject(GET_BY_ID, EMPLOYEE_ROW_MAPPER, id);
        return setRoles(employee);
    }

    @Override
    public int getEmployeeIdForClient(int clientId) {
        final int[] max = {0};
        final int[] employeeId = {0};
        jdbcTemplate.query(GET_EMPLOYEE_ID_FOR_CLIENT, resultSet -> {
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
        jdbcTemplate.update(DELETE_EMPLOYEE, id);
    }

    @Override
    public int save(Employee emp) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(emp);
        int key = jdbcInsert.executeAndReturnKey(source).intValue();
        emp.setId(key);
        insertRoles(emp);
        return key;
    }

    @Override
    public void update(Employee emp) {
        deleteRoles(emp);
        insertRoles(emp);
        jdbcTemplate.update(UPDATE,
                emp.getName(), emp.getPhone(), emp.getSalary(), emp.getPercent(), emp.getEmail(), emp.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = jdbcTemplate.queryForObject(GET_BY_EMAIL, EMPLOYEE_ROW_MAPPER, s);
        return new LoggedUser(setRoles(employee));
    }

    private Map<Integer, List<EmployeeRole>> getAllRoles() {
        return jdbcTemplate.query(GET_ALL_ROLES,
                (rs, rowNum) -> new EmployeeRole(Role.valueOf(rs.getString("role")), rs.getInt("user_id")))
                .stream().collect(Collectors.groupingBy(EmployeeRole::getUserId));
    }

    private void insertRolesAllEmployees(List<Employee> all, Map<Integer, List<EmployeeRole>> userRoles) {
        all.forEach(u -> u.setRoles(userRoles.get(u.getId()).stream().map(EmployeeRole::getRole).collect(Collectors.toSet())));
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

    private void getAndInsertAllRoles(List<Employee> all) {
        Map<Integer, List<EmployeeRole>> userRoles = getAllRoles();
        insertRolesAllEmployees(all, userRoles);
    }

    private class EmployeeRole {
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
}
