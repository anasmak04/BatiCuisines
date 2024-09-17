package main.java.ui;

import java.util.Scanner;

public class PrincipalMenu {

    private final ProjectMenu projectMenu;
    private static Scanner scanner;

    public PrincipalMenu(ProjectMenu projectMenu) {
        this.projectMenu = projectMenu;
        scanner = new Scanner(System.in);
    }


    public void Menu() {
        boolean check = true;

        while (check) {
            System.out.println("=== Welcome to the Kitchen Renovation Project Management Application ===");
            System.out.println("1. Create a new project");
            System.out.println("2. Display existing projects");
            System.out.println("3. Calculate project cost");
            System.out.println("4. Quit");
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
                    check = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

    }

    public void ProjectAddMenu() {
        this.projectMenu.addOrSearchClientMenu();
    }

    public void oldProjectsMenu() {
        this.projectMenu.findAll();
    }

    public void totalCost() {

    }

}