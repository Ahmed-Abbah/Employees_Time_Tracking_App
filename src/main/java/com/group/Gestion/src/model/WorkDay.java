package com.group.Gestion.src.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.jdbc.Work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static String getDayOfWeekName(String dateString) {
        try {
            // Define the input date format
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(dateString);

            // Create a Calendar instance and set the parsed date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Get the day of the week as an integer (Sunday = 1, Monday = 2, ..., Saturday = 7)
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // Define an array of day names
            String[] dayNames = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            // Use the day of the week to index the dayNames array
            return dayNames[dayOfWeek - 1];
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    public String getTotalWorkedTime() {
        try {
            // Define the input time format
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);

            // Calculate the time difference in milliseconds
            long timeDifference = date2.getTime() - date1.getTime();
            long totalPausesTime = 0;
            for(Pause  pause : this.getPauses() ){
                totalPausesTime = totalPausesTime + pause.getTotalPauseTime();
            }
            timeDifference = timeDifference - totalPausesTime;
            // Convert milliseconds to hours and minutes
            long hours = timeDifference / (60 * 60 * 1000);
            if(hours>=8){
                return "8 hour(s)";
            }
            long minutes = (timeDifference / (60 * 1000)) % 60;

            return hours + " hour(s) and " + minutes + " minute(s)";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unavailable Data";
        }
    }

    public String getTotalExtraTime() {
        try {
            // Define the input time format
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);

            // Calculate the time difference in milliseconds
            long timeDifference = date2.getTime() - date1.getTime();

            // Convert milliseconds to hours and minutes
            long hours = timeDifference / (60 * 60 * 1000);
            if(hours<=8){
                return "0 hour(s) and + 0 minute(s)";
            }
            hours=hours-8;
            long minutes = (timeDifference / (60 * 1000)) % 60;

            return hours + " hour(s) and " + minutes + " minute(s)";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unavailable Data";
        }
    }


}
