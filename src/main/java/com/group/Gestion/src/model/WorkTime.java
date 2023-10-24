package com.group.Gestion.src.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class WorkTime {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Date startTime;

    private Date endTime;

    @ManyToOne()
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "workTime",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Pause> pauses ;

}
