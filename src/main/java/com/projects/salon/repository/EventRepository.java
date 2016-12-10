package com.projects.salon.repository;

import com.projects.salon.entity.Event;

import java.util.List;

public interface EventRepository {
    List<Event> getAll();
}
