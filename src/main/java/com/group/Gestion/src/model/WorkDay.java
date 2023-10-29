package com.group.Gestion.src.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "date"})
)
public class WorkDay {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String date;

    private String startTime;

    private String endTime;

    @ManyToOne()
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "workDay",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Pause> pauses ;

}
