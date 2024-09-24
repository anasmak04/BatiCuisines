package main.java.ui;

import main.java.domain.entities.Devis;
import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.domain.enums.ProjectStatus;
import main.java.exception.ProjectNotFoundException;
import main.java.repository.impl.ComponentRepository;
import main.java.repository.impl.ProjectRepository;
import main.java.service.DevisService;
import main.java.service.MaterialService;
import main.java.service.WorkForceService;
import main.java.utils.DateFormat;

import java.time.LocalDate;
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

        System.out.print("Enter project Name: ");
        String projectName = scanner.nextLine();

        Project project = projectRepository.findProjectByName(projectName).orElseThrow(
                () -> new ProjectNotFoundException("project name not found")
        );


        List<Material> materials = materialService.findAllByProjectId(project.getId());
        List<WorkForce> workforce = workForceService.findAllByProjectId(project.getId());


        double totalMaterialBeforeVat = materials.stream()
                .mapToDouble(materialService::calculateMaterialBeforeVatRate)
                .sum();

        double totalMaterialAfterVat = materials.stream()
                .mapToDouble(materialService::calculateMaterialAfterVatRate)
                .sum();

        double totalWorkforceBeforeVat = workforce.stream()
                .mapToDouble(workForceService::calculateWorkforceBeforeVat)
                .sum();

        double totalWorkforceAfterVat = workforce.stream()
                .mapToDouble(workForceService::calculateWorkforceAfterVat)
                .sum();


        double totalCostAfterVat = totalMaterialAfterVat + totalWorkforceAfterVat;
        double totalCostBeforeMargin = totalCostAfterVat;

        double totalCost = totalCostAfterVat;

        double marginRate = 0.0;
        if (getYesNoInput("Do you want to apply a profit margin to the project? (y/n): ")) {
            System.out.print("Enter profit margin percentage: ");
            marginRate = scanner.nextDouble();
            scanner.nextLine();
            project.setProfitMargin(marginRate);
            double profitMargin = totalCost * marginRate / 100;
            totalCost += profitMargin;
        }

        projectRepository.updateProjectFields(project.getId(), project.getProfitMargin(), totalCost);

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

        if (project.getClient().isProfessional()) {
            System.out.println("\n--- Professional Client Discount Applied ---");
            System.out.println("Enter client discount");
            double discount = scanner.nextDouble();
            scanner.nextLine();
            totalCost *= discount;
            System.out.print("Discounted Total Cost: " + String.format("%.2f", totalCost) + " €");
        }

        System.out.print("Enter issue date (yyyy-MM-dd): ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateParse = DateFormat.parseDate(issueDate);
        System.out.print("Enter validated date (yyyy-MM-dd): ");
        String validatedDate = scanner.nextLine();
        LocalDate validatedDateParse = DateFormat.parseDate(validatedDate);
        Devis devis = new Devis(0L, totalCost, issueDateParse, validatedDateParse, false, project);
        devisService.save(devis);

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(validatedDateParse)) {
            System.out.println("The Devis is pending validation until " + validatedDateParse + ".");
            System.out.println("You can accept or reject it before this date.");
            return;
        }

        System.out.print("Do you want to accept the Devis? (Yes/No): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("yes") || choice.equals("y")) {
            devisService.updateDevisStatus(devis.getId());
            projectRepository.updateProjectStatus(project.getId(), ProjectStatus.FINISHED.name());
            System.out.println("Devis accepted. Project marked as FINISHED.");
        } else {
            devisService.cancelDevisAndProjectIfNotAccepted(devis.getId(), validatedDateParse);
            projectRepository.updateProjectStatus(project.getId(), ProjectStatus.CANCELLED.name());
            System.out.println("Devis rejected. Project marked as CANCELLED.");
        }

    }


}
