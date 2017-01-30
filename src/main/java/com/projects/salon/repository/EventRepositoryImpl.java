package com.projects.salon.repository;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepositoryImpl.class);
    private static final RowMapper<Event> EVENT_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);
    private static final RowMapper<EmailRecord> RECORD_ROW_MAPPER = BeanPropertyRowMapper.newInstance(EmailRecord.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Event> getAll(int employeeId) {
        LOGGER.debug("Returns all events.");
        return jdbcTemplate.query(
                "SELECT id,title,start,\"end\",sum,client_id,employee_id FROM events WHERE employee_id=?",
                EVENT_ROW_MAPPER, employeeId);
    }

    @Override
    public Event getById(int id) {
        LOGGER.debug("Return event: {}", id);
        return jdbcTemplate.queryForObject("SELECT title,start,\"end\",sum,client_id,employee_id FROM events WHERE id=?",
                EVENT_ROW_MAPPER, id);
    }

    @Override
    public void save(Event event) {
        LOGGER.debug("Save event: {}.", event);
        jdbcTemplate.update(new SavePreparedStatementCreator(event));
    }

    @Override
    public void update(Event event) {
        LOGGER.debug("Update event: {}; {}", event.getId(), event);
        jdbcTemplate.update(new UpdatePreparedStatementCreator(event));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM events WHERE id=?", id);
    }

    @Override
    public void payEvent(int id, int sum) {
        jdbcTemplate.update("UPDATE events SET sum=? WHERE id=?", sum, id);
    }

    @Override
    public List<EmailRecord> getTomorrowsForEmployee(int id) {
        return jdbcTemplate.query("SELECT\n" +
                "  start,\n" +
                "  name\n" +
                "FROM events evn\n" +
                "  LEFT JOIN clients cl ON evn.client_id = cl.id\n" +
                "WHERE\n" +
                "  ((start < current_timestamp + INTERVAL '1 day') AND (start > current_timestamp))\n" +
                "  AND employee_id=?", RECORD_ROW_MAPPER, id);
    }

    private class SavePreparedStatementCreator implements PreparedStatementCreator {
        private final Event event;

        SavePreparedStatementCreator(Event event) {
            this.event = event;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO events(client_id, employee_id, title, start, \"end\") VALUES (?,?,?,?,?)");
            statement.setInt(1, event.getClientId());
            statement.setInt(2, event.getEmployeeId());
            statement.setString(3, event.getTitle());
            statement.setObject(4, Timestamp.valueOf(event.getStart()), Types.TIMESTAMP);
            statement.setObject(5, Timestamp.valueOf(event.getEnd()), Types.TIMESTAMP);
            return statement;
        }
    }

    private class UpdatePreparedStatementCreator implements PreparedStatementCreator {
        private final Event event;

        UpdatePreparedStatementCreator(Event event) {
            this.event = event;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement statement = con.prepareStatement(
                    "UPDATE events SET title=?,sum=?,start=?,\"end\"=? WHERE id=?");
            statement.setString(1, event.getTitle());
            statement.setInt(2, event.getSum());
            statement.setObject(3, Timestamp.valueOf(event.getStart()), Types.TIMESTAMP);
            statement.setObject(4, Timestamp.valueOf(event.getEnd()), Types.TIMESTAMP);
            statement.setInt(5, event.getId());
            return statement;
        }
    }
}
