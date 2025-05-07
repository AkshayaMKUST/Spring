package org.division;

import org.division.exception.IncorrectDataPassedException;

import java.util.Scanner;

public class Division {
    public void performDivision()throws IncorrectDataPassedException {
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the dividend : ");
                int dividend = scanner.nextInt();
                System.out.println("Enter the divisor : ");
                int divisor = scanner.nextInt();
                if (divisor==0){
                    throw new IncorrectDataPassedException("Divisor you have entered is zero");
                }
                else{
                    System.out.println("Divident used : "+ dividend);
                    System.out.println("Divisor used : "+divisor);
                    float result = (float) dividend /divisor;
                    System.out.println("Division result : "+result);
                }
            }
            catch(IncorrectDataPassedException e){
                e.printStackTrace();
                throw new IncorrectDataPassedException(e.getMessage());
            }
        }
    }

