package com.projects.salon.service;

import com.projects.salon.entity.Event;
import com.projects.salon.repository.EmployeeRepository;
import com.projects.salon.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public boolean checksEventIsFreeFor(String date, String time, int clientId) {
        LocalTime localTime = LocalTime.parse(time);
        if (localTime.isBefore(LocalTime.parse("08:59")) || localTime.isAfter(LocalTime.parse("19:00"))) {
            return false;
        }
        String[] monthDay = date.split("-");
        int employeeId = employeeRepository.getEmployeeIdForClient(clientId);
        List<Event> events = eventRepository.checkFreeDate(Integer.parseInt(monthDay[0]), Integer.parseInt(monthDay[1]), employeeId);
        for (Event event : events) {
            LocalTime startLocalTime = event.getStart().toLocalTime();
            if (startLocalTime.equals(localTime)) {
                return false;
            }
            if (startLocalTime.isBefore(localTime) &&
                    event.getEnd().toLocalTime().isAfter(localTime)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void save(int clientId, String title, String start) {
        LocalDateTime startEvent = parseDateTime(start);
        LocalDateTime endEvent = startEvent.plusHours(1);
        int employeeId = employeeRepository.getEmployeeIdForClient(clientId);
        eventRepository.save(new Event(null, clientId, employeeId, title, startEvent, endEvent, 0));
    }

    private LocalDateTime parseDateTime(String start) {
        String result = parseMonth(start);

        //add year
        result = getCurrentYear() + "-" + result;

        result = result.replace(" ", "T");
        return LocalDateTime.parse(result, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private String parseMonth(String start) {
        if (start.substring(0, start.indexOf("-")).length() == 1) {
            return 0 + start;
        } else {
            return start;
        }
    }

    private int getCurrentYear() {
        return LocalDateTime.now().toLocalDate().getYear();
    }
}
