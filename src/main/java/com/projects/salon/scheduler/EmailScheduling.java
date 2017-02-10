package com.projects.salon.scheduler;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Employee;
import com.projects.salon.repository.EmployeeRepository;
import com.projects.salon.repository.EventRepository;
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

    private final EventRepository eventRepository;
    private final EmployeeRepository employeeRepository;
    private final MailSender mailSender;

    @Autowired
    public EmailScheduling(EventRepository eventRepository, EmployeeRepository employeeRepository, MailSender mailSender) {
        this.eventRepository = eventRepository;
        this.employeeRepository = employeeRepository;
        this.mailSender = mailSender;
    }

    @Async
    @Scheduled(cron = "0 0 22 * * *", zone = "Europe/Kiev")
    public void test() {
        List<Employee> employees = employeeRepository.getAllWithoutAdmin();
        for (Employee employee : employees) {
            List<EmailRecord> tomorrowsForEmployee = eventRepository.getTomorrowsForEmployee(employee.getId());
            log.info("Tomorrow records for {}: {}", employee.getName(), tomorrowsForEmployee);
            mailSender.sendMessage(employee, tomorrowsForEmployee);
        }
    }
}
