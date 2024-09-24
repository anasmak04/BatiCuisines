package main.java.ui;

import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.domain.enums.ComponentType;
import main.java.exception.LaborNotFoundException;
import main.java.service.ComponentService;
import main.java.service.WorkForceService;
import main.java.utils.WorkforceHelper;

import java.util.List;
import java.util.Scanner;

public class WorkForceMenu {
    private final WorkForceService workForceService;
    private final ComponentService componentService;
    private final Scanner scanner;

    public WorkForceMenu(WorkForceService workForceService, ComponentService componentService) {
        this.workForceService = workForceService;
        this.componentService = componentService;
        this.scanner = new Scanner(System.in);
    }


    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n--- Workforce Management Menu ---");
            System.out.println("1. Add Workforce");
            System.out.println("2. Update Workforce");
            System.out.println("3. Delete Workforce");
            System.out.println("4. Find Workforce by ID");
            System.out.println("5. List All Workforces");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addWorkForce(new Project());
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    findById();
                    break;
                case 5:
                    findAll();
                    break;
                case 0:
                    System.out.println("Exiting Workforce Management.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);
    }

    public WorkForce addWorkForce(Project project) {
        String continueChoice;
        WorkForce workForce = null;
        do {

            workForce = WorkforceHelper.getWorkforceDetails(null);
            workForceService.save(workForce);
            System.out.print("Would you like to add another workforce? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return workForce;
    }


    public void update() {
        System.out.print("Enter id of workforce : ");
        Long id = scanner.nextLong();
        workForceService.findById(id).orElseThrow(() -> new LaborNotFoundException("workforce not found"));
        scanner.nextLine();


        WorkForce workForce1 = WorkforceHelper.getWorkforceDetails(id);
        this.workForceService.update(workForce1);
    }

    public void delete() {
        System.out.print("Enter a workforce Id : ");
        Long id = scanner.nextLong();
        this.workForceService.delete(id);
    }


    public void findById() {
        System.out.print("Enter the workforce ID: ");
        Long id = scanner.nextLong();

        workForceService.findById(id).ifPresentOrElse(workForce -> {
            System.out.printf("| %-10s | %-15s | %-15s | %-15s | %-20s |%n",
                    "ID", "Hourly Cost", "Working Hours", "Productivity", "Component Name");
            System.out.println("-------------------------------------------------------------------------------");

            System.out.printf("| %-10d | %-15.2f | %-15.2f | %-15.2f | %-20s |%n",
                    workForce.getId(),
                    workForce.getHourlyCost(),
                    workForce.getWorkingHours(),
                    workForce.getWorkerProductivity(),
                    workForce.getName());

            System.out.println("-------------------------------------------------------------------------------");
        }, () -> {
            System.out.println("Workforce with ID " + id + " not found.");
            System.out.println("-------------------------------------------------------------------------------");
        });
    }


    public void findAll() {
        System.out.println("--- List of All Workforce ---");

        List<WorkForce> workForces = workForceService.findAll();

        System.out.printf("| %-10s | %-15s | %-15s | %-15s | %-20s |%n",
                "ID", "Hourly Cost", "Working Hours", "Productivity", "Component Name");
        System.out.println("-------------------------------------------------------------------------------");

        if (workForces.isEmpty()) {
            System.out.println("| No workforce found.");
            System.out.println("-------------------------------------------------------------------------------");
            return;
        }

        workForces.forEach(workForce -> {
            System.out.printf("| %-10d | %-15.2f | %-15.2f | %-15.2f | %-20s |%n",
                    workForce.getId(),
                    workForce.getHourlyCost(),
                    workForce.getWorkingHours(),
                    workForce.getWorkerProductivity(),
                    workForce.getName());
        });

        System.out.println("-------------------------------------------------------------------------------");
    }


}
