package com.group.Gestion.src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.group.Gestion.src.model.Employee;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
