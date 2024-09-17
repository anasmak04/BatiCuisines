package main.java.ui;

import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.repository.ComponentRepository;
import main.java.repository.ProjectRepository;

import java.util.Scanner;

public class CostCalculationMenu {
    private static Scanner scanner;
    private ProjectRepository projectRepository;
    private ComponentRepository componentRepository;
    
    public CostCalculationMenu(ProjectRepository projectRepository, ComponentRepository componentRepository) {
        scanner = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.componentRepository = componentRepository;
    }

    private static boolean getYesNoInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.next().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    public void save() {
        System.out.println("--- Total Cost Calculation ---");

        System.out.print("Enter the base cost of the project: ");
        double baseCost = scanner.nextDouble();

        Component component = new Component();
        Project project = new Project();

        if (getYesNoInput("Do you want to apply VAT to the project? (y/n): ")) {
            System.out.print("Enter VAT percentage: ");
            double vatRate = scanner.nextDouble();
            component.setVatRate(vatRate);
        }

        if (getYesNoInput("Do you want to apply a profit margin to the project? (y/n): ")) {
            System.out.print("Enter profit margin percentage: ");
            double marginRate = scanner.nextDouble();
            project.setProfitMargin(marginRate);
        }

        double vatRate = component.getVatRate();
        double marginRate = project.getProfitMargin();

        double vatAmount = baseCost * vatRate / 100;

        double costAfterVat = baseCost + vatAmount;

        double marginAmount = costAfterVat * marginRate / 100;

        double totalCost = costAfterVat + marginAmount;

        System.out.printf("Base Cost: %.2f%n", baseCost);

        System.out.printf("Total Cost: %.2f%n", totalCost);

    }
}
