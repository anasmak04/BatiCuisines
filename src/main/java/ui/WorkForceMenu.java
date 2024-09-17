package main.java.ui;

import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.domain.enums.ComponentType;
import main.java.service.ComponentService;
import main.java.service.WorkForceService;

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

    public WorkForce addWorkForce(Project project) {
        String continueChoice;
        WorkForce workForce = null;
        do {
            System.out.println("--- Add Workforce ---");

            System.out.print("Enter the name of the Workforce: ");
            String name = scanner.nextLine();

            System.out.print("Enter the VAT rate of the workforce: ");
            double vatRate = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter the hourly rate for this labor (€/h): ");
            double hourlyRate = scanner.nextDouble();

            System.out.print("Enter the number of hours worked: ");
            double hoursWorked = scanner.nextDouble();

            System.out.print("Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
            double productivityFactor = scanner.nextDouble();
            scanner.nextLine();

            Component component = new Component();
            component.setName(name);
            component.setComponentType(ComponentType.WORKFORCE.name());
            component.setVatRate(vatRate);
            component.setProject(project);

            Component savedComponent = componentService.save(component);

            workForce = new WorkForce(0L, name, "workforce", vatRate, project, hourlyRate, hoursWorked, productivityFactor, savedComponent);
            workForceService.save(workForce);

            System.out.print("Would you like to add another workforce? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return workForce;
    }


    public void update(WorkForce workForce) {
        this.workForceService.update(workForce);
    }

    public void delete(Long id) {
        this.workForceService.delete(id);
    }

    public void findById(Long id) {
        this.workForceService.findById(id);
    }

    public void findAll() {
        this.workForceService.findAll();
    }
}
