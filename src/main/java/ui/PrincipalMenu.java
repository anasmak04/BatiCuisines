package main.java.ui;

import main.java.domain.entities.Client;

import java.util.Scanner;

public class PrincipalMenu {

    private final ProjectMenu projectMenu;
    private static Scanner scanner;
    private final DevisMenu devisMenu;
    private final MaterialMenu materialMenu;
    private final ClientMenu clientMenu;
    private final WorkForceMenu workForceMenu;
    private final CostCalculationMenu costCalculationMenu;

    public PrincipalMenu(ProjectMenu projectMenu, DevisMenu devisMenu, ClientMenu clientMenu, CostCalculationMenu costCalculationMenu, MaterialMenu materialMenu, WorkForceMenu workForceMenu) {
        this.projectMenu = projectMenu;
        this.devisMenu = devisMenu;
        this.clientMenu = clientMenu;
        this.costCalculationMenu = costCalculationMenu;
        this.materialMenu = materialMenu;
        this.workForceMenu = workForceMenu;
        scanner = new Scanner(System.in);
    }


    public void Menu() {
        boolean check = true;

        while (check) {
            System.out.println("=== Welcome to the Kitchen Renovation Project Management Application ===");
            System.out.println("1. Create a new project");
            System.out.println("2. Display existing projects");
            System.out.println("3. Calculate project cost");
            System.out.println("4. Devis Menu");
            System.out.println("5. Client Menu");
            System.out.println("6. Material Menu");
            System.out.println("7. Workforce Menu");
            System.out.println("8. Quit");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ProjectAddMenu();
                    break;
                case 2:
                    oldProjectsMenu();
                    break;
                case 3:
                    totalCost();
                    break;
                case 4:
                    devisMenu();
                    break;
                case 5:
                    devisMenu();
                    break;
                case 6:
                    materialMenu();
                    break;
                case 7:
                    workforceMenu();
                    break;
                case 8:
                    check = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

    }

    private void ProjectAddMenu() {
        this.projectMenu.addOrSearchClientMenu();
    }

    private void oldProjectsMenu() {
        this.projectMenu.findAll();
    }

    private void devisMenu() {
        this.devisMenu.displayMenu();
    }

    private void clientMenu() {
        this.clientMenu.clientMenu();
    }

    private void totalCost() {
        costCalculationMenu.save();
    }

    private void materialMenu() {
        materialMenu.materialMenu();
    }

    private void workforceMenu() {
        workForceMenu.displayMenu();
    }


}
