package com.projects.salon.repository;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Event;

import java.util.List;

public interface EventRepository {
    List<Event> getAll(int employeeId);

    List<Event> checkFreeDate(int month, int day, int employeeId);

    Event getById(int id);

    void save(Event event);

    void update(Event event);

    void delete(int id);

    void payEvent(int id, int sum);

    List<EmailRecord> getTomorrowsForEmployee(int id);
}
