package com.example.demo.sevice;

import com.example.demo.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    void sendMail(NotificationEmail notificationEmail) throws MailException {
        MimeMessagePreparator mimeMessagePreparator = (mimeMessage) -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("amanraihot@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setText(notificationEmail.getBody());
        };
        mailSender.send(mimeMessagePreparator);
    }




}
