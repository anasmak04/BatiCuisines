package main.java.utils;

import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;

import java.util.Scanner;

public class WorkforceHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static WorkForce getWorkforceDetails(Long id) {
        if (id != null) {
            System.out.println("\n--- Update Workforce ---");
        } else {
            System.out.println("\n--- Add New Workforce ---");
        }

        System.out.print("Enter the name of the Workforce: ");
        String name = scanner.nextLine();

        System.out.print("Enter the VAT rate of the workforce: ");
        double vatRate = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the hourly rate for this labor (€/h): ");
        double hourlyRate = scanner.nextDouble();

        System.out.print("Enter the number of hours worked: ");
        double hoursWorked = scanner.nextDouble();

        Project project = new Project();

        System.out.print("Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
        double productivityFactor = scanner.nextDouble();

        return new WorkForce(id != null ? id : 0L, name, "workforce", vatRate, project, hourlyRate, hoursWorked, productivityFactor);
    }
}
