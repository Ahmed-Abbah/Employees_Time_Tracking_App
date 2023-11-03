package com.group.Gestion.src;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.repository.EmployeeRepository;
import com.group.Gestion.src.service.EmailService;
import com.group.Gestion.src.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class SpringEmailApplication {
    @Autowired
    private PdfGeneratorService pdfGeneratorService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String baseUrl = "http://localhost:912"; // Remplacez par votre URL

    public static void main(String[] args) {
        SpringApplication.run(SpringEmailApplication.class, args);
    }

    @Scheduled(cron = "0 07 19 * * ?")
    public void sendMail() {
        List<Employee> employees = employeeRepository.findAll();
        String subject = "Formulaire de Feedback";

        for (Employee employee : employees) {
            if (employee.getLastEmailSent() != null) {
                LocalTime lastEmailSentTime = employee.getLastEmailSent().toLocalTime();
                LocalTime currentTime = LocalTime.now();
                if (currentTime.isAfter(LocalTime.of(19, 5)) && currentTime.isBefore(LocalTime.of(18, 11)) &&
                        lastEmailSentTime.isAfter(LocalTime.of(19, 5)) && lastEmailSentTime.isBefore(LocalTime.of(18, 11))) {
                    continue;
                }
            }

            String feedbackFormUrl = baseUrl + "/feedback";

            String body = "Cher " + employee.getFirstName() + ",\n\n";
            body += "Veuillez cliquer sur le lien ci-dessous pour accéder au formulaire de feedback :\n";
            body += feedbackFormUrl + "\n\n";
            body += "Cordialement,\nVotre entreprise";

            emailService.sendEmailToEmployee(employee, subject, body);

            employee.setLastEmailSent(LocalDateTime.now());
            employeeRepository.save(employee);
        }
    }

    public ByteArrayOutputStream generatePdf() throws IOException {
        ByteArrayOutputStream pdfStream = this.pdfGeneratorService.generateEmployeeReports();
        return pdfStream;
    }

    @Scheduled(cron = "0 0 17 * * SAT")
    public void sendRapport() throws IOException {
        ByteArrayOutputStream pdfStream = generatePdf();
        String subject = "Rapport Hebdomadaire";
        String body = "Cher Administrateur, \n\n";

        emailService.sendEmailToAdminWithAttachment(subject, body, pdfStream, "rapport.pdf");
    }
}
