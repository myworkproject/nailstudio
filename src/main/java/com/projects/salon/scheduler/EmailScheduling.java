package com.projects.salon.scheduler;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Employee;
import com.projects.salon.service.EmployeeService;
import com.projects.salon.service.EventService;
import com.projects.salon.service.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
public class EmailScheduling {
    private final EventService eventService;
    private final EmployeeService employeeService;
    private final MailSender mailSender;

    @Autowired
    public EmailScheduling(EventService eventRepository, EmployeeService employeeRepository, MailSender mailSender) {
        this.eventService = eventRepository;
        this.employeeService = employeeRepository;
        this.mailSender = mailSender;
    }

    @Async
    @Scheduled(cron = "0 0 22 * * *", zone = "Europe/Kiev")
    public void test() {
        List<Employee> employees = employeeService.getAllWithoutAdmin();
        for (Employee employee : employees) {
            List<EmailRecord> tomorrowsForEmployee = eventService.getTomorrowsForEmployee(employee.getId());
            log.info("Tomorrow records for {}: {}", employee.getName(), tomorrowsForEmployee);
            mailSender.sendMessage(employee, tomorrowsForEmployee);
        }
    }
}
