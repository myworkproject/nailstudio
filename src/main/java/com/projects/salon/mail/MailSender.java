package com.projects.salon.mail;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Employee;

import java.util.List;

public interface MailSender {
    void sendMessage(Employee employee, List<EmailRecord> records);
}
