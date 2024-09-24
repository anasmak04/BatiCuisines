package main.java.utils;

import main.java.domain.entities.Material;
import main.java.domain.entities.Project;

import java.util.Scanner;

public class MaterialHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static Material getMaterialDetails(Long id, Project project) {
        if (id != null) {
            System.out.println("\n--- Add New Material ---");
        } else {
            System.out.println("\n--- Update Material ---");
        }

        System.out.print("Enter the name of the material: ");
        String name = scanner.nextLine();

        System.out.print("Enter the quantity of this material: ");
        double quantity = scanner.nextDouble();

        System.out.print("Enter the unit cost of the material (€/m² or €/litre): ");
        double unitCost = scanner.nextDouble();

        System.out.print("Enter the transport cost of the material (€): ");
        double transportCost = scanner.nextDouble();

        System.out.print("Enter the quality coefficient of the material (1.0 = standard, > 1.0 = high quality): ");
        double coefficientQuality = scanner.nextDouble();

        System.out.print("Enter the VAT rate of the material: ");
        double vatRate = scanner.nextDouble();
        scanner.nextLine();
       return new Material(id != null ? id : 0L, name, "Material", vatRate, project, unitCost, quantity, transportCost, coefficientQuality);
    }
}
