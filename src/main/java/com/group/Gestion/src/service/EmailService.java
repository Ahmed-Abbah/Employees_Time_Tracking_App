package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
}