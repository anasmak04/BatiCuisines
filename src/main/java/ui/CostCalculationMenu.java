package main.java.ui;

import main.java.domain.entities.Project;

import java.util.Scanner;

public class CostCalculationMenu {
    private static Scanner scanner;

    public CostCalculationMenu() {
        scanner = new Scanner(System.in);
    }

    private static boolean getYesNoInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.next().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }


    public void save() {
        System.out.println("--- Total Cost Calculation ---");

        System.out.println("Do you want to apply VAT to the project? (y/n):");
        String input1 = scanner.next().trim().toLowerCase();
        if (input1.equals("y") || input1.equals("yes")) {
            System.out.println("Enter VAT amount:");
            double vatAmount = scanner.nextDouble();
        }
        System.out.println("\"Do you want to apply a profit margin to the project? (y/n):");
        String input2 = scanner.next().trim().toLowerCase();
        if (input2.equals("y") || input2.equals("yes")) {
            System.out.print("Enter percentage of marge :");
            double percentage = scanner.nextDouble();
        }
        Project project = new Project();
    }

}
