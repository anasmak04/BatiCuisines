package main.java.ui;

import main.java.domain.entities.Client;
import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.domain.enums.ProjectStatus;
import main.java.service.ProjectService;

import java.util.Scanner;

public class ProjectMenu {

    private final ProjectService projectService;
    private final ClientMenu clientMenu;
    private final Scanner scanner;
    private Client selectedClient;
    private final MaterialMenu materialMenu;
    private final WorkForceMenu workForceMenu;

    public ProjectMenu(ProjectService projectService, ClientMenu clientMenu, MaterialMenu materialMenu, WorkForceMenu workForceMenu) {
        this.projectService = projectService;
        this.clientMenu = clientMenu;
        this.materialMenu = materialMenu;
        this.workForceMenu = workForceMenu;
        this.scanner = new Scanner(System.in);
    }

    public void addOrSearchClientMenu() {
        while (true) {
            System.out.println("\n--- Search for a Client ---");
            System.out.println("1. Search for an Existing Client");
            System.out.println("2. Add a New Client");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchClient();
                    break;
                case 2:
                    addNewClient();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchClient() {
        System.out.println("\n--- Search for an Existing Client ---");
        System.out.print("Enter the name of the client: ");
        String name = scanner.nextLine();
        Client optionalClient = clientMenu.searchByName(name);
        if (optionalClient != null) {
            selectedClient = optionalClient;
            System.out.println("Client found!");
            System.out.println("Name: " + selectedClient.getName());
            System.out.println("Address: " + selectedClient.getAddress());
            System.out.println("Phone number: " + selectedClient.getPhone());
            addProject();
        } else {
            System.out.println("Client not found.");
            selectedClient = null;
        }
    }

    private void addNewClient() {
        selectedClient = clientMenu.addNewClient();
        if (selectedClient != null) {
            addProject();
        } else {
            System.out.println("Failed to add new client.");
        }
    }

    private void addProject() {
        try {
            System.out.println("\n--- Add a New Project ---");
            System.out.print("Enter the name of the project: ");
            String name = scanner.nextLine();
            System.out.print("Enter the surface area for the project: ");
            double surface = scanner.nextDouble();
            scanner.nextLine();

            Project project = new Project(0L, name, 0, 0, ProjectStatus.INPROGRESS.name(), surface, selectedClient);
            Project savedProject = projectService.save(project);

            Material savedMaterial = materialMenu.addMaterial(savedProject);
            WorkForce savedWorkForce = workForceMenu.addWorkForce(savedProject);

//            savedProject.addComponent(savedMaterial.getComponent());
//            savedProject.addComponent(savedWorkForce.getComponent());

        } catch (Exception e) {
            System.out.println("An error occurred while adding the project: " + e.getMessage());
        }
    }


    public void findAll() {
        projectService.findAll().forEach(project -> {
            System.out.println("--- Project Details ---");
            System.out.println("ID: " + project.getId());
            System.out.println("Name: " + project.getProjectName());
            System.out.println("Surface: " + project.getSurface());
            System.out.println("Status: " + project.getStatus());
            System.out.println("Profit Margin: " + project.getProfitMargin());
            System.out.println("Total Cost: " + project.getTotalCost());

            Client client = project.getClient();
            if (client != null) {
                System.out.println("Client Name: " + client.getName());
                System.out.println("Client Phone: " + client.getPhone());
                System.out.println("Client Address: " + client.getAddress());
            } else {
                System.out.println("Client: Not available");
            }

            System.out.println("--- Components ---");
            project.getComponents().forEach(component -> {
                System.out.println("Component ID: " + component.getId());
                System.out.println("Type: " + component.getComponentType());
                System.out.println("Name: " + component.getName());
                System.out.println("VAT Rate: " + component.getVatRate());
                component.getMaterials().forEach(
                        material -> {
                            System.out.println("Material ID: " + material.getId());
                            System.out.println("Material: " + material.getName());
                            System.out.println("VAT Rate: " + material.getVatRate());

                        }
                );

                component.getWorkForces().forEach(workForce -> {
                    System.out.println("Work Force ID: " + workForce.getId());
                    System.out.println("Work Force: " + workForce.getName());
                    System.out.println("Work Force: " + workForce.getName());

                });
                System.out.println();
            });


            System.out.println("-------------\n");
        });
    }

}
