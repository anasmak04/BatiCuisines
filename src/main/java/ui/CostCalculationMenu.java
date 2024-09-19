package main.java.ui;


import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.exception.DevisNotFoundException;
import main.java.repository.ComponentRepository;
import main.java.repository.ProjectRepository;
import main.java.service.DevisService;
import main.java.service.MaterialService;
import main.java.service.WorkForceService;

import java.util.List;
import java.util.Scanner;

public class CostCalculationMenu {
    private static Scanner scanner;
    private final ProjectRepository projectRepository;
    private final ComponentRepository componentRepository;
    private final MaterialService materialService;
    private final WorkForceService workForceService;
    private final DevisService devisService;
    private final DevisMenu devisMenu;

    public CostCalculationMenu(ProjectRepository projectRepository, ComponentRepository componentRepository,
                               MaterialService materialService, WorkForceService workForceService, DevisService devisService, DevisMenu devisMenu) {
        this.devisService = devisService;
        this.devisMenu = devisMenu;
        scanner = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.componentRepository = componentRepository;
        this.materialService = materialService;
        this.workForceService = workForceService;
    }

    private static boolean getYesNoInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.next().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    public void save() {
        System.out.println("--- Total Cost Calculation ---");

        System.out.print("Enter project ID: ");
        Long projectId = scanner.nextLong();

        Project project = projectRepository.findById(projectId).orElseThrow(() ->
                new RuntimeException("Project not found"));


        List<Material> materials = materialService.findAllByProjectId(projectId);
        List<WorkForce> workforce = workForceService.findAllByProjectId(projectId);

        double totalMaterialBeforeVat = 0;
        double totalMaterialAfterVat = 0;

        for (Material material : materials) {
            double materialCostBeforeVat = materialService.calculateMaterialBeforeVatRate(material);
            double materialCostAfterVat = materialService.calculateMaterialAfterVatRate(material);

            totalMaterialBeforeVat += materialCostBeforeVat;
            totalMaterialAfterVat += materialCostAfterVat;
        }

        double totalWorkforceBeforeVat = 0;
        double totalWorkforceAfterVat = 0;

        for (WorkForce workForce : workforce) {
            double workforceCostBeforeVat = workForceService.calculateWorkforceBeforeVat(workForce);
            double workforceCostAfterVat = workForceService.calculateWorkforceAfterVat(workForce);

            totalWorkforceBeforeVat += workforceCostBeforeVat;
            totalWorkforceAfterVat += workforceCostAfterVat;
        }

        double totalCostBeforeMargin = totalMaterialBeforeVat + totalWorkforceBeforeVat;
        double totalCostAfterVat = totalMaterialAfterVat + totalWorkforceAfterVat;

        double totalCost = totalCostAfterVat;
        double marginRate = 0.0;
        if (getYesNoInput("Do you want to apply a profit margin to the project? (y/n): ")) {
            System.out.print("Enter profit margin percentage: ");
            marginRate = scanner.nextDouble();
            project.setProfitMargin(marginRate);
            double profitMargin = totalCost * marginRate / 100;
            totalCost += profitMargin;
        }

        projectRepository.updateProjectFields(projectId, project.getProfitMargin(), totalCost);
        devisService.updateAmountDevis(projectId, totalCost);


        System.out.println("\n--- Calculation Result ---");
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Client: " + project.getClient().getName());
        System.out.println("Address: " + project.getClient().getAddress());
        System.out.println("Area: " + project.getSurface() + " m²");
        System.out.println("--- Cost Details ---");
        System.out.println("Materials Cost Before VAT: " + String.format("%.2f", totalMaterialBeforeVat) + " €");
        System.out.println("Materials Cost After VAT: " + String.format("%.2f", totalMaterialAfterVat) + " €");
        System.out.println("Workforce Cost Before VAT: " + String.format("%.2f", totalWorkforceBeforeVat) + " €");
        System.out.println("Workforce Cost After VAT: " + String.format("%.2f", totalWorkforceAfterVat) + " €");
        System.out.println("Total Cost Before Margin: " + String.format("%.2f", totalCostBeforeMargin) + " €");
        System.out.println("id __>" + projectId);
        try {
            devisMenu.findDevisByProject(projectId);
        } catch (DevisNotFoundException devisNotFoundException) {
            System.out.println(devisNotFoundException.getMessage());
        }
    }


}
