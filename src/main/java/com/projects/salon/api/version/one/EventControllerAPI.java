package com.projects.salon.api.version.one;

import com.projects.salon.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0.1/events")
@Slf4j
public class EventControllerAPI {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity saveEvent(@RequestParam String clientId, @RequestParam String title) {
        log.info("SAVE EVENT FROM VIBER: client={}, title={}.", clientId, title);
        return new ResponseEntity(HttpStatus.OK);
    }
}
