package com.projects.salon.controller;

import com.projects.salon.entity.Event;
import com.projects.salon.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventRestController.class);

    @Autowired
    private EventRepository eventRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Event> getAll() {
//        LOGGER.debug("Returns all events.");
        return eventRepository.getAll();
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable int id) {
        return eventRepository.getById(id);
    }

    @PostMapping
    public int saveOrUpdateAndReturnKey(Event event) {
//        LOGGER.debug("Save event: {}.", event);
        if (event.getId() == null) {
            return eventRepository.saveAndReturnKey(event);
        } else {
            return eventRepository.updateAndReturnKey(event);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        eventRepository.delete(id);
    }
}
