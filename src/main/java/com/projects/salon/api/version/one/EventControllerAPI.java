package com.projects.salon.api.version.one;

import com.projects.salon.entity.Event;
import com.projects.salon.repository.EmployeeRepository;
import com.projects.salon.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v0.1/events")
@Slf4j
public class EventControllerAPI {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public ResponseEntity saveEvent(@RequestParam String clientId, @RequestParam String title, @RequestParam String start) {
        log.info("SAVING EVENT FROM VIBER...");
        LocalDateTime startEvent = parseDateTime(start);
        LocalDateTime endEvent = startEvent.plusHours(1);
        int employeeId = employeeRepository.getEmployeeIdForClient(Integer.parseInt(clientId));
        eventRepository.save(new Event(null, Integer.parseInt(clientId), employeeId, title, startEvent, endEvent, 0));
        log.info("SAVE OK!");
        return new ResponseEntity(HttpStatus.OK);
    }

    private LocalDateTime parseDateTime(String start) {
        String result;
        if (start.substring(0, start.indexOf("-")).length() == 1) {
            result = 0 + start;
        } else {
            result = start;
        }
        //add year
        result = getCurrentYear() + "-" + result;

        result = result.replace(" ", "T");
        return LocalDateTime.parse(result, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private int getCurrentYear() {
        return LocalDateTime.now().toLocalDate().getYear();
    }
}
