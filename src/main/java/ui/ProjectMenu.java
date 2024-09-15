package main.java.ui;

import main.java.domain.entities.Client;
import main.java.domain.entities.Project;
import main.java.domain.enums.ProjectStatus;
import main.java.repository.ProjectRepository;

import java.util.Scanner;

public class ProjectMenu {

    private final ProjectRepository projectRepository;
    private static Scanner scanner;

    public ProjectMenu(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.scanner = new Scanner(System.in);
    }

    public void addNewProject() {
        try {
            Project project = inputProject();
            projectRepository.saveClientProject(project.getClient(), project);
            System.out.println("Project and Client saved successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while saving: " + e.getMessage());
        }
    }

    private static Client inputClient() {
        System.out.println("Enter Client Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Is Professional (true/false): ");
        boolean isProfessional = scanner.nextBoolean();
        scanner.nextLine();

        return new Client(0, name, address, phone, isProfessional);
    }

    private static Project inputProject() {
        Client client = inputClient();

        System.out.println("\nEnter Project Details:");
        System.out.print("Project Name: ");
        String projectName = scanner.nextLine();

        System.out.print("Profit Margin: ");
        double profitMargin = scanner.nextDouble();

        System.out.print("Total Cost: ");
        double totalCost = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Status (PENDING/IN_PROGRESS/COMPLETED): ");
        String statusInput = scanner.nextLine().toUpperCase();
        ProjectStatus status = ProjectStatus.valueOf(statusInput);
        return new Project(0, projectName, profitMargin, totalCost, status.name(), client);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Project Management Menu ---");
            System.out.println("1. Add New Project");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewProject();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}