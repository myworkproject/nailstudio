package com.projects.salon.repository;

import com.projects.salon.entity.SalaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SalaryRepositoryImpl implements SalaryRepository {
    private static final RowMapper<SalaryInfo> SALARY_INFO_ROW_MAPPER = BeanPropertyRowMapper.newInstance(SalaryInfo.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SalaryRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<SalaryInfo> getEmployeesInfo(int month) {
        return jdbcTemplate.query("SELECT name,year,month,total,salary FROM employee_salary WHERE year = 2017 and month=?",
                SALARY_INFO_ROW_MAPPER, month);
    }

    @Override
    public SalaryInfo getAdminInfo(int month) {
        SalaryInfo admin = new SalaryInfo();
        jdbcTemplate.query("SELECT\n" +
                "  extract(YEAR FROM evn.start)  AS year,\n" +
                "  extract(MONTH FROM evn.start) AS month,\n" +
                "  sum(evn.sum)                  AS total\n" +
                "FROM events evn\n" +
                "GROUP BY year, month\n" +
                "HAVING extract(YEAR FROM evn.start) = 2017\n" +
                "       AND extract(MONTH FROM evn.start) = ?", (resultSet, i) -> {
            admin.setYear(resultSet.getInt("year"));
            admin.setMonth(resultSet.getInt("month"));
            admin.setTotal(resultSet.getInt("total"));
            return admin;
        }, month);
        jdbcTemplate.query("SELECT\n" +
                "  name,\n" +
                "  salary,\n" +
                "  percent\n" +
                "FROM employees\n" +
                "WHERE admin=true;", new RowMapper<SalaryInfo>() {
            @Override
            public SalaryInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                admin.setName(resultSet.getString("name"));
                int salary = resultSet.getInt("salary");
                int percent = resultSet.getInt("percent");
                int finalSalary = (admin.getTotal() * percent / 100) + salary;
                admin.setSalary(finalSalary);
                return admin;
            }
        });
        return admin;
    }
}
