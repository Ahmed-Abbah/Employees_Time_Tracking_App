package com.group.Gestion.src;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.repository.EmployeeRepository;
import com.group.Gestion.src.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Scheduled(cron = "0 16 18 * * ?") // Planifiez l'envoi à 13:44 tous les jours
    public void sendMail() {
        List<Employee> employees = employeeRepository.findAll();
        String subject = "Formulaire de Feedback";

        for (Employee employee : employees) {
            // Vérifiez si l'employé a déjà ouvert le lien aujourd'hui
            if (employee.getLastEmailSent() != null) {
                LocalTime lastEmailSentTime = employee.getLastEmailSent().toLocalTime();
                LocalTime currentTime = LocalTime.now();
                if (currentTime.isAfter(LocalTime.of(18, 12)) && currentTime.isBefore(LocalTime.of(18, 11)) &&
                        lastEmailSentTime.isAfter(LocalTime.of(18, 12)) && lastEmailSentTime.isBefore(LocalTime.of(18, 11))) {
                    // Le lien a déjà été ouvert après 13:44, donc le lien est expiré
                    continue;
                }
            }

            String feedbackFormUrl = baseUrl + "/feedback";

            String body = "Cher " + employee.getFirstName() + ",\n\n";
            body += "Veuillez cliquer sur le lien ci-dessous pour accéder au formulaire de feedback :\n";
            body += feedbackFormUrl + "\n\n";
            body += "Cordialement,\nVotre entreprise";

            emailService.sendEmailToEmployee(employee, subject, body);

            // Mettez à jour l'heure de l'envoi de l'e-mail
            employee.setLastEmailSent(LocalDateTime.now());
            employeeRepository.save(employee);
        }
    }
}
