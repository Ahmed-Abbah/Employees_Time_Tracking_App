package com.group.Gestion.src.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WorkTime> workTimes;

    public Employee(Employee employee) {
    }
}
