package com.projects.salon.controller;

import com.projects.salon.entity.Event;
import com.projects.salon.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventRestController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping(value = "/all/{employeeId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Event> getAll(@PathVariable int employeeId) {
        log.debug("Returns all events.");
        return eventRepository.getAll(employeeId);
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable int id) {
        log.debug("Return event {}.", id);
        return eventRepository.getById(id);
    }

    @PostMapping
    public void saveOrUpdate(Event event) {
        if (event.getId() == null) {
            log.debug("Save event: {}.", event);
            eventRepository.save(event);
        } else {
            log.debug("Update event: {}.", event);
            eventRepository.update(event);
        }
    }

    @PostMapping("/pay/{id}")
    public void payEvent(@PathVariable int id, int sum) {
        log.debug("Event {}, pay {}.", id, sum);
        eventRepository.payEvent(id, sum);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.debug("Delete event {}.", id);
        eventRepository.delete(id);
    }
}
