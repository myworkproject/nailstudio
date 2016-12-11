package com.projects.salon.repository;

import com.projects.salon.entity.Event;

import java.util.List;

public interface EventRepository {
    List<Event> getAll();

    Event getById(int id);

    int saveAndReturnKey(Event event);

    int updateAndReturnKey(Event event);

    void delete(int id);
}
