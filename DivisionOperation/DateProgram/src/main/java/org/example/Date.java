package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Date {
    public void checkDate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the date in YYYY-MM-DD format : ");
        String enteredDateStr = sc.next();
        LocalDate currentDate = LocalDate.now();
        LocalDate enteredDate = LocalDate.parse(enteredDateStr,DateTimeFormatter.ISO_DATE);
        if(enteredDate.equals(currentDate)){
            System.out.println("The entered date "+enteredDate+" is matching with today's date");
        }
        else if(enteredDate.isBefore(currentDate)){
            System.out.println("The entered date "+enteredDate+" is before today's date");
        }
        else {
            System.out.println("The entered date "+enteredDate+" is after today's date");
        }
    }

    public void changeDateFormat(){
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        System.out.println("The current date format is changed to MM-dd-yyyy : "+formattedDate);
    }
}
