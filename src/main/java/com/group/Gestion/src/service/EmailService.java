package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailToEmployee(Employee employee, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wissalelouafy12@gmail.com");
        message.setTo(employee.getEmail());
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        System.out.println("Mail sent successfully to " + employee.getEmail());
    }

    public void sendEmailToAdminWithAttachment(String subject, String text, ByteArrayOutputStream attachmentStream, String attachmentFileName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("wissalelouafy12@gmail.com");
            helper.setTo("hajar.elamri@etu.uae.ac.ma");
            helper.setSubject(subject);
            helper.setText(text, true);

            // Add an attachment
            DataSource attachment = new ByteArrayDataSource(attachmentStream.toByteArray(), "application/pdf");
            helper.addAttachment(attachmentFileName, attachment);

            javaMailSender.send(message);
            System.out.println("Mail sent successfully to " + "hajar");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}