package com.group.Gestion.src;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Role;
import com.group.Gestion.src.repository.EmployeeRepository;
import com.group.Gestion.src.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SrcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrcApplication.class, args);
	}


	@Bean
	CommandLineRunner run(RoleRepository roleRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			Employee admin = Employee.builder()
					.firstName("Administrateur")
					.email("admin@admin.com")
					.password(passwordEncoder.encode("admin"))
					.authorities(roles)
					.build();
			employeeRepository.save(admin);

		};
	}
}
