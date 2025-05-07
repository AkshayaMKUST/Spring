package org.example;

import java.util.Scanner;

public class Calculator {
    public void calculate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter operator (+, -, *, /): ");
        char operator = scanner.next().charAt(0);
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        scanner.close();
        Operation operation;
//        switch (operator) {
//            case '+':
//                operation = (x, y) -> x + y;
//                System.out.println("Addition Result : "+operation.operate(num1,num2));
//                break;
//            case '-':
//                operation = (x, y) -> x - y;
//                System.out.println("Subtraction Result : "+operation.operate(num1,num2));
//                break;
//            case '*':
//                operation = (x, y) -> x * y;
//                System.out.println("Multiplication Result : "+operation.operate(num1,num2));
//                break;
//            case '/':
//                operation = (x, y) -> x / y;
//                System.out.println("Division Result : "+operation.operate(num1,num2));
//                break;
//            default:
//                System.out.println("Invalid operator");
//                break;
//        }
        operation = switch (operator) {
            case '+' -> (x, y) -> x + y;
            case '-' -> (x, y) -> x - y;
            case '*' -> (x, y) -> x * y;
            case '/' -> (x, y) -> x / y;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
        System.out.println(operation.operate(num1, num2));
    }
}
