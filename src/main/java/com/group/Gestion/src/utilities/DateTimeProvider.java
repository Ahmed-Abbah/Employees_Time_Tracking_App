package com.group.Gestion.src.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeProvider {
    public static String getCurrentDate() {

        LocalDate currentDate = LocalDate.now();


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return currentDate.format(dateFormatter);
    }
    public static String getCurrentTime() {

        LocalTime currentTime = LocalTime.now();


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


        return currentTime.format(timeFormatter);
    }
}
