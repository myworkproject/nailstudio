package com.projects.salon.api.version.one;

import com.projects.salon.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0.1/events")
@Slf4j
public class EventControllerAPI {

    private final EventService eventService;

    @Autowired
    public EventControllerAPI(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity saveEvent(@RequestParam String clientId, @RequestParam String title, @RequestParam String start) {
        log.info("SAVING EVENT FROM VIBER...");
        eventService.save(Integer.parseInt(clientId), title, start);
        log.info("SAVE OK!");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/date")
    public ResponseEntity checkDate(@RequestParam String start, @RequestParam String time, @RequestParam String clientId) {
        log.info("Check date and time {} {} for client: {}.", start, time, clientId);
        if (eventService.checksEventIsFreeFor(start, time, Integer.parseInt(clientId))) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
