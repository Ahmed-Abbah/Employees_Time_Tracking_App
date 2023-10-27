package com.group.Gestion.src.service;

import com.group.Gestion.src.DTOs.LoginResponseDto;
import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Role;
import com.group.Gestion.src.repository.EmployeeRepository;
import com.group.Gestion.src.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    private EmployeeRepository employeeRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AuthenticationService(EmployeeRepository employeeRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenService tokenService
    ) {
        this.employeeRepository = employeeRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.tokenService=tokenService;
    }

    public Employee registerUser(String username,String password,String firstName,String lastName){
        String encodedPassword = passwordEncoder.encode(password);
        Role employeeRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(employeeRole);
        return employeeRepository.save(Employee.builder()
                        .email(username)
                        .password(encodedPassword)
                        .firstName(firstName)
                        .lastName(lastName)
                        .authorities(authorities)
                .build());
    }

    public LoginResponseDto loginEmployee(String username,String password){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDto(employeeRepository.findByEmail(username).get(),token);
        }catch(AuthenticationException e){
            return new LoginResponseDto(null,"");
        }

    }
}
