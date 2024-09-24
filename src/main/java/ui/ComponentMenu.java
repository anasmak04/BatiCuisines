package main.java.ui;

import java.util.Scanner;

public class ComponentMenu {
    private final MaterialMenu materialMenu;
    private final WorkForceMenu workForceMenu;
    private final static Scanner scanner = new Scanner(System.in);

    public ComponentMenu(MaterialMenu materialMenu, WorkForceMenu workForceMenu) {
        this.materialMenu = materialMenu;
        this.workForceMenu = workForceMenu;
    }

    public void menu() {
        int choice = 0;

        while (choice != 3) {
            System.out.println("1- Material Menu");
            System.out.println("2- Workforce Menu");
            System.out.println("3- Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    materialMenu();
                    break;
                case 2:
                    workForceMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }

    public void workForceMenu() {
        this.workForceMenu.displayMenu();
    }

    public void materialMenu() {
        this.materialMenu.materialMenu();
    }
}
