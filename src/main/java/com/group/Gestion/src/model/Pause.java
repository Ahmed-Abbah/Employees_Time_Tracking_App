package com.group.Gestion.src.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Pause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String startTime;

    private String endTime;

    @ManyToOne()
    @JoinColumn(name = "workDay_id",nullable = false)
    private WorkDay workDay;

}
