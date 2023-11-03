package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeService implements UserDetailsService{
    private EmployeeRepository employeeRepository;
    private PasswordEncoder encoder;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository,PasswordEncoder encoder) {
        this.employeeRepository = employeeRepository;
        this.encoder=encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In user service specifically loadByUsername function");
//        if(!username.equals("abbah.ahmed@etu.uae.ac.ma")) throw new UsernameNotFoundException("Not Ahmed");
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role(1,"USER"));
//        return new Employee().builder()
//                .firstName("Ahmed")
//                .lastName("Abbah")
//                .email("abbah.ahmed@etu.uae.ac.ma")
//                .authorities(roles)
//                .password(encoder.encode("password"))
//                .build();

        return employeeRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    public Employee findEmployeeById(long id) {
       return this.employeeRepository.findEmployeeById(id);
    }

    public Employee save(Employee employee, HttpSession http){
        http.setAttribute("loggedInEmployee",employee);
        return this.employeeRepository.save(employee);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(username);
//        return employee.map(Employee::new).orElseThrow(()->new UsernameNotFoundException("Email or password is Incorrect ."));
//    }

    public long getTotalEmployeeCount() {
        return employeeRepository.count();
    }
        public Employee save(Employee employee) {
            return employeeRepository.save(employee);
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }
}
