package main.java.ui;

import main.java.domain.entities.Devis;
import main.java.domain.entities.Project;
import main.java.domain.enums.ProjectStatus;
import main.java.exception.DevisNotFoundException;
import main.java.exception.ProjectNotFoundException;
import main.java.repository.impl.ProjectRepository;
import main.java.service.DevisService;
import main.java.service.ProjectService;
import main.java.utils.DateFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DevisMenu {

    private final Scanner scanner;
    private final DevisService devisService;
    private final ProjectService projectService;

    public DevisMenu(DevisService devisService, ProjectService projectService) {
        this.devisService = devisService;
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nDevis Management Menu");
            System.out.println("1. Save Devis");
            System.out.println("2. Delete Devis");
            System.out.println("3. Find All Devis");
            System.out.println("4. Find Devis by ID");
            System.out.println("5. Update Devis");
            System.out.println("6. Accept  Devis");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    saveDevis();
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    findAll();
                    break;
                case 4:
                    findById();
                    break;
                case 5:
                    update();
                    break;
                case 6:
                    acceptDevis();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void save(Devis devis){
        devisService.save(devis);
    }


    private void saveDevis() {
        System.out.print("Enter Project Name: ");
        String projectName = scanner.nextLine();
        Project project = null;
        try {
            project = projectService.findProjectByName(projectName);
        } catch (ProjectNotFoundException projectNotFoundException) {
            System.out.println(projectNotFoundException.getMessage());
            return;
        }

        System.out.print("Enter estimated amount: ");
        double estimatedAmount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter issue date (yyyy-MM-dd): ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateParse = DateFormat.parseDate(issueDate);

        System.out.print("Enter validated date (yyyy-MM-dd): ");
        String validatedDate = scanner.nextLine();
        LocalDate validatedDateParse = DateFormat.parseDate(validatedDate);

        Devis devis = new Devis(0L, estimatedAmount, issueDateParse, validatedDateParse, false, project);
        devisService.save(devis);
    }


    private void delete() {
        System.out.print("Enter Devis ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        devisService.delete(id);
    }

    public void findAll() {
        List<Devis> devisList = devisService.findAll();
        System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");
        System.out.printf("| %-12s | %-17s | %-12s | %-12s | %-11s | %-18s | %-18s |%n",
                "Devis ID", "Estimated Amount", "Issue Date", "Validated Date", "Is Accepted", "Project Name", "Client Name");
        System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");

        devisList.forEach(devis -> {
            System.out.printf("| %-12d | %-17.2f | %-12s | %-12s | %-11b | %-18s | %-18s |%n",
                    devis.getId(),
                    devis.getEstimatedAmount(),
                    devis.getIssueDate(),
                    devis.getValidatedDate(),
                    devis.isAccepted(),
                    devis.getProject().getProjectName(),
                    devis.getProject().getClient().getName()
            );
        });

        System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");
    }


    private void findById() {
        System.out.print("Enter Devis ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Optional<Devis> devis = devisService.findById(id);

        devis.ifPresent(devis1 -> {
            System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");
            System.out.printf("| %-12s | %-17s | %-12s | %-12s | %-11s | %-18s | %-18s |%n",
                    "Devis ID", "Estimated Amount", "Issue Date", "Validated Date", "Is Accepted", "Project Name", "Client Name");
            System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");

            System.out.printf("| %-12d | %-17.2f | %-12s | %-12s | %-11b | %-18s | %-18s |%n",
                    devis1.getId(),
                    devis1.getEstimatedAmount(),
                    devis1.getIssueDate(),
                    devis1.getValidatedDate(),
                    devis1.isAccepted(),
                    devis1.getProject().getProjectName(),
                    devis1.getProject().getClient().getName()
            );
            System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");
        });
    }


    public void update() {
        System.out.print("Enter Devis ID: ");
        Long id = scanner.nextLong();
        System.out.print("Enter estimatedAmount");
        double estimatedAmount = scanner.nextDouble();
        System.out.print("Enter issueDate (yyyy-MM-dd) : ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateParse = DateFormat.parseDate(issueDate);
        System.out.print("Enter validated date (yyyy-MM-dd): ");
        String validatedDate = scanner.nextLine();
        LocalDate validatedDateParse = DateFormat.parseDate(validatedDate);
        System.out.print("Enter Project Name: ");
        String projectName = scanner.nextLine();
        Project project = null;
        try {
            project = projectService.findProjectByName(projectName);
        } catch (ProjectNotFoundException projectNotFoundException) {
            System.out.println(projectNotFoundException.getMessage());
            return;
        }
        Devis devis = new Devis(id, estimatedAmount, issueDateParse, validatedDateParse, false, project);
        devisService.update(devis);
    }

    public void findDevisByProject(Long projectId) {
        Optional<Devis> devisOptional = this.devisService.findDevisByproject(projectId);

        devisOptional.ifPresent(devis1 -> {
            System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");
            System.out.printf("| %-12s | %-17s | %-12s | %-12s | %-11s | %-18s | %-18s |%n",
                    "Devis ID", "Estimated Amount", "Issue Date", "Validated Date", "Is Accepted", "Project Name", "Client Name");
            System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");

            System.out.printf("| %-12d | %-17.2f | %-12s | %-12s | %-11b | %-18s | %-18s |%n",
                    devis1.getId(),
                    devis1.getEstimatedAmount(),
                    devis1.getIssueDate(),
                    devis1.getValidatedDate() != null ? devis1.getValidatedDate() : "N/A",
                    devis1.isAccepted(),
                    devis1.getProject().getProjectName() != null ? devis1.getProject().getProjectName() : "N/A",
                    devis1.getProject().getClient().getName() != null ? devis1.getProject().getClient().getName() : "N/A"
            );

            System.out.printf("+--------------+-------------------+--------------+--------------+-------------+--------------------+--------------------+%n");
        });

        devisOptional.orElseThrow(() -> new DevisNotFoundException("Devis not found!"));
    }

    public void acceptDevis() {
        Project project = null;

        System.out.println("Enter Project Name: ");
        String projectName = scanner.nextLine();

        try {
            project = this.projectService.findProjectByName(projectName);
        } catch (ProjectNotFoundException projectNotFoundException) {
            System.out.println(projectNotFoundException.getMessage());
            return;
        }

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        Long projectId = project.getId();
        Optional<Devis> devis = Optional.empty();

        try {
            devis = this.devisService.findDevisByproject(projectId);
        } catch (DevisNotFoundException devisNotFoundException) {
            System.out.println(devisNotFoundException.getMessage());
            return;
        }

        if (devis.isPresent()) {
            System.out.println("Do you want to accept this devis? (y/n): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                this.devisService.updateDevisStatus(devis.get().getId());
                this.projectService.updateProjectStatus(projectId , ProjectStatus.FINISHED.name());
                System.out.println("Devis accepted.");
                System.out.println("Project accepted.");
            } else {
                System.out.println("Devis not accepted.");
            }
        } else {
            System.out.println("Devis not found.");
        }
    }


}
