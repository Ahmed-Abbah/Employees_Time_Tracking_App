package com.group.Gestion.src;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.repository.EmployeeRepository;
import com.group.Gestion.src.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@EnableScheduling // Active la planification des tâches
public class SpringEmailApplication {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String baseUrl = "http://localhost:911"; // Remplacez par votre URL

    public static void main(String[] args) {
        SpringApplication.run(SpringEmailApplication.class, args);
    }

    @Scheduled(cron = "0 24 11 * * ?") // Planifiez l'exécution à 13:55 tous les jours
    public void sendMail() {
        List<Employee> employees = employeeRepository.findAll();
        String subject = "Formulaire de Feedback";

        for (Employee employee : employees) {
            String feedbackFormUrl = baseUrl + "/feedback";

            String body = "Cher " + employee.getFirstName() + ",\n\n";
            body += "Veuillez cliquer sur le lien ci-dessous pour accéder au formulaire de feedback :\n";
            body += feedbackFormUrl + "\n\n";
            body += "Cordialement,\nVotre entreprise";

            emailService.sendEmailToEmployee(employee, subject, body);
        }
    }
}