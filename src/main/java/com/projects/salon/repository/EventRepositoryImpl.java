package com.projects.salon.repository;

import com.projects.salon.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private static final RowMapper<Event> EVENT_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public List<Event> getAll() {
        return jdbcTemplate.query("SELECT id,title,start,end FROM events", EVENT_ROW_MAPPER);
    }
}
