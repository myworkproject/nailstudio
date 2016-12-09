package com.projects.salon.repository;

import com.projects.salon.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private static final RowMapper<Client> CLIENT_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Client.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Client> getAll() {
        return jdbcTemplate.query("SELECT id,name,phone FROM clients", CLIENT_ROW_MAPPER);
    }

    @Override
    public Client getById(int id) {
        return jdbcTemplate.queryForObject("SELECT id,name,phone FROM clients WHERE id=?", CLIENT_ROW_MAPPER, id);
    }
}
