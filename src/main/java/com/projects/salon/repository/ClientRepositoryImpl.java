package com.projects.salon.repository;

import com.projects.salon.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private static final RowMapper<Client> CLIENT_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Client.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ClientRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("clients")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Client> getAll() {
        return jdbcTemplate.query("SELECT id,first_name,last_name,phone FROM clients", CLIENT_ROW_MAPPER);
    }

    @Override
    public Client getById(int id) {
        return jdbcTemplate.queryForObject("SELECT id,first_name,last_name,phone FROM clients WHERE id=?", CLIENT_ROW_MAPPER, id);
    }

    @Override
    public Client getByTelephone(String telephone) {
        return jdbcTemplate.queryForObject("SELECT id,first_name,last_name,phone FROM clients WHERE phone=?", CLIENT_ROW_MAPPER, telephone);
    }

    @Override
    public int save(Client client) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(client);
        return jdbcInsert.executeAndReturnKey(source).intValue();
    }

    @Override
    public void update(Client client) {
        jdbcTemplate.update("UPDATE clients SET first_name=?,last_name=?,phone=? WHERE id=?",
                client.getFirstName(), client.getLastName(), client.getPhone(), client.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM clients WHERE id=?", id);
    }
}
