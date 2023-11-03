package com.group.Gestion.src.repository;

import com.group.Gestion.src.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);
    Employee findEmployeeById(long id);
    List<Employee> findAll();

/*    List<Employee> findByLastEmailSentBefore(LocalDateTime currentDateTime);*/
}
