package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(username);
//        return employee.map(Employee::new).orElseThrow(()->new UsernameNotFoundException("Email or password is Incorrect ."));
//    }
}
