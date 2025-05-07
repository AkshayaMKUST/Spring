package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Date {
    public void checkDate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the date in YYYY-HH-DD format : ");
        String inputDateStr = sc.next();

        LocalDate inputDate = LocalDate.parse(inputDateStr, DateTimeFormatter.ISO_DATE);
        if(inputDate.equals(LocalDate.now())){
            System.out.println("The given date is matching with today's date");
        } else if (inputDate.isBefore(LocalDate.now()) ) {
            System.out.println("The given date is before today's date");
        }
        else if(inputDate.isAfter(LocalDate.now())){
            System.out.println("The given date is after today's date");
        }
    }
    public void changeDateFormat(){
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        System.out.println("The current date format "+currentDate+" is formatted to "+formattedDate);
    }
}
