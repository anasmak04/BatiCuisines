package main.java.ui;

import main.java.domain.entities.Component;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.domain.enums.ComponentType;
import main.java.exception.LaborNotFoundException;
import main.java.service.ComponentService;
import main.java.service.WorkForceService;

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


    public void update() {
        System.out.print("Enter id of workforce : ");
        Long id = scanner.nextLong();
        WorkForce workForce = workForceService.findById(id).orElseThrow(() -> new LaborNotFoundException("workforce not found"));
        System.out.print("Enter name of workforce : ");
        String name = scanner.nextLine();
        System.out.print("Enter number of hours worked : ");
        double hoursWorked = scanner.nextDouble();
        System.out.print("Enter number of hourly cost : ");
        double hourlyCost = scanner.nextDouble();
        System.out.print("Enter productivity factor : ");
        double productivityFactor = scanner.nextDouble();

        Component component = new Component();
        System.out.print("Enter the name of component ");
        String componentName = scanner.nextLine();
        System.out.print("Enter vat rate : ");
        double vatRate = scanner.nextDouble();
        component.setName(name);
        component.setComponentType(ComponentType.WORKFORCE.name());
        component.setVatRate(vatRate);
        Project project = new Project();
        WorkForce workForce1 = new WorkForce(id, componentName, ComponentType.WORKFORCE.name(), vatRate, project, hourlyCost, hoursWorked, productivityFactor, component);
        this.workForceService.update(workForce);
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
            System.out.println("\n--- Workforce Details ---");
            System.out.println("ID: " + workForce.getId());
            System.out.println("Component Name: " + workForce.getComponent().getName());
            System.out.println("Hourly Cost: " + workForce.getHourlyCost());
            System.out.println("Working Hours: " + workForce.getWorkingHours());
            System.out.println("Worker Productivity: " + workForce.getWorkerProductivity());
        }, () -> {
            System.out.println("Workforce with ID " + id + " not found.");
        });
    }


    public void findAll() {
        System.out.println("--- List of All Workforce ---");

        List<WorkForce> workForces = workForceService.findAll();
        workForces.forEach(workForce -> {
            System.out.println("\n--- Workforce Details ---");
            System.out.println("ID: " + workForce.getId());
            System.out.println("Component Name: " + workForce.getComponent().getName());
            System.out.println("Hourly Cost: " + workForce.getHourlyCost());
            System.out.println("Working Hours: " + workForce.getWorkingHours());
            System.out.println("Worker Productivity: " + workForce.getWorkerProductivity());
        });
    }


}
