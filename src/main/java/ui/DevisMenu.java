package main.java.ui;

import main.java.domain.entities.Devis;
import main.java.domain.entities.Project;
import main.java.service.DevisService;
import main.java.utils.DateFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class DevisMenu {

    private Scanner scanner;
    private DevisService devisService;

    public DevisMenu(DevisService devisService) {
        this.devisService = devisService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nDevis Management Menu");
            System.out.println("1. Save Devis");
            System.out.println("2. Delete Devis");
            System.out.println("3. Find All Devis");
            System.out.println("4. Find Devis by ID");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    saveDevis();
                    break;
                case 2:
                    deleteDevis();
                    break;
                case 3:
                    findAll();
                    break;
                case 4:
                    findById();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void saveDevis() {
        System.out.print("Enter Project ID: ");
        int projectId = scanner.nextInt();
        scanner.nextLine();
        Project project = new Project();

        System.out.print("Enter estimated amount: ");
        double estimatedAmount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter issue date (yyyy-MM-dd): ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateParse = DateFormat.parseDate(issueDate);

        System.out.print("Enter validated date (yyyy-MM-dd): ");
        String validatedDate = scanner.nextLine();
        LocalDate validatedDateParse = DateFormat.parseDate(validatedDate);

        Devis devis = new Devis(0, estimatedAmount, issueDateParse, validatedDateParse, false, project);
        devisService.save(devis);
    }

    private void deleteDevis() {
        System.out.print("Enter Devis ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        devisService.delete(id);
    }

    private void findAll() {
        List<Devis> devisList = devisService.findAll();
    }

    private void findById() {
        System.out.print("Enter Devis ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        devisService.findById(id);
    }
}
