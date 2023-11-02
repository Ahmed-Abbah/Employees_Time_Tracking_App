package com.group.Gestion.src.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

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



    public long getTotalPauseTime() {
        try {
            // Define the input time format
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);

            // Calculate the time difference in milliseconds
            long timeDifference = date2.getTime() - date1.getTime();

            return timeDifference;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getTotalPauseTimeInMinutes(){

        Integer totalTime =(int)this.getTotalPauseTime()/60000;

        return totalTime + " min";
    }


}
