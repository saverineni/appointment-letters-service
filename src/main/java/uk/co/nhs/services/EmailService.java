package uk.co.nhs.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uk.co.nhs.api.model.Email;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            mailSender.send(message);
        } catch (Exception e) {
            log.error("E-mail could not be sent to user " + email.getTo() + ", exception : " + e.getMessage());
        }
    }
}
