package org.interest;

import java.util.Scanner;

public class Interest implements Calculate {
    @Override
    public void simpleInterest(float principleAmount, float rate, float time){
        float simpleInterest = (principleAmount * rate * time)/100;
        System.out.println("Simple Interest : "+ simpleInterest);
    }
    @Override
    public void compoundInterest(float principleAmount, float rate, float time, float number) {

        float amount = principleAmount * (float) Math.pow((1 + rate / number), number * time);
        float compoundInterest = amount - principleAmount;
        System.out.println("Compound interest is " + compoundInterest);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the principal amount: ");
        float principleAmount = scanner.nextFloat();
        System.out.print("Enter the annual interest rate : ");
        float rate = scanner.nextFloat();
        System.out.print("Enter the time period : ");
        float time = scanner.nextFloat();
        System.out.print("Enter the number of time interest compounded : ");
        float number = scanner.nextFloat();

        Interest ob = new Interest();
        ob.simpleInterest(principleAmount,rate,time);
        ob.compoundInterest(principleAmount,rate,time,number);
    }
}
