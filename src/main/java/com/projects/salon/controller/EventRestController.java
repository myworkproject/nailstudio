package com.projects.salon.controller;

import com.projects.salon.entity.Event;
import com.projects.salon.service.EventService;
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
    private EventService eventService;

    @GetMapping(value = "/all/{employeeId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Event> getAll(@PathVariable int employeeId) {
        log.debug("Returns all events.");
        return eventService.getAll(employeeId);
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable int id) {
        log.debug("Return event {}.", id);
        return eventService.getById(id);
    }

    @PostMapping
    public void saveOrUpdate(Event event) {
        if (event.getId() == null) {
            log.debug("Save event: {}.", event);
            eventService.save(event);
        } else {
            log.debug("Update event: {}.", event);
            eventService.update(event);
        }
    }

    @PostMapping("/pay/{id}")
    public void payEvent(@PathVariable int id, int sum) {
        log.debug("Event {}, pay {}.", id, sum);
        eventService.payEvent(id, sum);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.debug("Delete event {}.", id);
        eventService.delete(id);
    }
}
