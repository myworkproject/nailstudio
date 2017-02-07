package com.projects.salon.config;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Employee;
import com.projects.salon.repository.EmployeeRepository;
import com.projects.salon.repository.EventRepository;
import com.projects.salon.service.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EmailScheduling {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailScheduling.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MailSender mailSender;

    @Async
    @Scheduled(cron = "0 40 22 * * *")
    public void test() {
        List<Employee> employees = employeeRepository.getAllWithoutAdmin();
        for (Employee employee : employees) {
            List<EmailRecord> tomorrowsForEmployee = eventRepository.getTomorrowsForEmployee(employee.getId());
            LOGGER.info("TOMORROW RECORDS FOR {}: {}", employee.getName(), tomorrowsForEmployee);
            mailSender.sendMessage(employee, tomorrowsForEmployee);
        }
    }
}
