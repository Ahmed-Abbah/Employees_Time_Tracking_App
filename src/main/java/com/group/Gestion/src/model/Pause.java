package com.group.Gestion.src.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Pause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date startTime;

    private Date endTime;

    @ManyToOne()
    @JoinColumn(name="work_time_id",nullable = false)
    private WorkTime workTime;

}
