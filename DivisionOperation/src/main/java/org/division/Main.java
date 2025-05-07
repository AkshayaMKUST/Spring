package org.division;

import org.division.exception.IncorrectDataPassedException;

public class Main {
    public static void main(String[] args){
        try {
            Division division = new Division();
            division.performDivision();
        }
        catch (IncorrectDataPassedException e){
           System.out.println("Error : "+ e.getMessage());
        }
    }
}