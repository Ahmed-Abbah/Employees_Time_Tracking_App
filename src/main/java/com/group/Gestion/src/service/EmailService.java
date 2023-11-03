package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, EmployeeRepository employeeRepository) {
        this.javaMailSender = javaMailSender;
        this.employeeRepository = employeeRepository;
    }

    public void sendEmailToUsersWithUserRole(String subject, String text) {
        List<Employee> usersWithUserRole = employeeRepository.findByRole("user");
        for (Employee user : usersWithUserRole) {
            sendEmailToEmployee(user, subject, text);
        }
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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wissalelouafy12@gmail.com");
        message.setTo("hajar.elamri@etu.uae.ac.ma");
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        System.out.println("Mail sent successfully to hajar.elamri@etu.uae.ac.ma");
    }
}
