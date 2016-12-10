package com.projects.salon.controller;

import com.projects.salon.entity.Event;
import com.projects.salon.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventRestController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
