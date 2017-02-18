package com.projects.salon.mail;

import com.projects.salon.entity.EmailRecord;
import com.projects.salon.entity.Employee;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MailSenderImpl implements MailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderImpl.class);

    @Override
    public void sendMessage(Employee employee, List<EmailRecord> records) {
        try {
            if (!records.isEmpty() && employee.getEmail() != null) {
                LOGGER.info("Prepare email send...");
                Email from = new Email("DariaZhadanNailStudio@nailstudio.com");
                String subject = employee.getName() + " " + records.get(0).getStart().toLocalDate().toString();
                Email to = new Email(employee.getEmail());
                Content content = new Content("text/html", prepareMailContent(records));
                Mail mail = new Mail(from, subject, to, content);

                SendGrid sg = new SendGrid(System.getenv("SENDGRID_KEY"));
                Request request = new Request();

                request.method = Method.POST;
                request.endpoint = "mail/send";
                request.body = mail.build();
                Response response = sg.api(request);
                if (response.statusCode == 202) {
                    LOGGER.info("Email send OK!");
                }
            }
        } catch (IOException e) {
            LOGGER.error("Email send error: {}.", e.getMessage());
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
}
