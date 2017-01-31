package com.projects.salon.service;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class MailSenderImpl implements MailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);

    private static Session mailSession = getMailSession();

    @Override
    public void sendMessage(Employee employee, List<EmailRecord> records) {
        try {
            if (!records.isEmpty()) {
                LOGGER.info("Prepare message...");
                MimeMessage emailMessage = new MimeMessage(mailSession);
                emailMessage.setSubject(employee.getName() + " " + records.get(0).getStart().toLocalDate().toString());
                emailMessage.setText(prepareMailContent(records), "UTF-8", "html");
                emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(employee.getEmail()));

                LOGGER.info("Try send message...");
                Transport transport = mailSession.getTransport();
                transport.connect();
                transport.sendMessage(emailMessage, emailMessage.getRecipients(Message.RecipientType.TO));
                transport.close();
                LOGGER.info("Message send: OK!");
            }
        } catch (MailException | MessagingException e) {
            LOGGER.error("ERROR: {}", e.getMessage());
        }
    }

    private String prepareMailContent(List<EmailRecord> records) {
        String table = "<table><thead>" +
                "<tr>" +
                "   <th>Время</th>" +
                "   <th>Имя</th>" +
                "</tr>" +
                "</thead><tbody>";
        for (EmailRecord record : records) {
            table += "<tr>" +
                    "<td>" + record.getStart().toLocalTime() + "</td>" +
                    "<td>" + record.getName() + "</td>" +
                    "</tr>";
        }
        table += "</tbody></table>";
        return table;
    }

    private static Session getMailSession() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");

        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("daria.zhadan.nailstudio@gmail.com", "an727123");
                    }
                });
    }
}
