package main.java.ui;

import main.java.domain.entities.Component;
import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.enums.ComponentType;
import main.java.service.ComponentService;
import main.java.service.MaterialService;

import java.util.Scanner;

public class MaterialMenu {
    private final MaterialService materialService;
    private final ComponentService componentService;
    private final Scanner scanner;

    public MaterialMenu(MaterialService materialService, ComponentService componentService) {
        this.materialService = materialService;
        this.componentService = componentService;
        this.scanner = new Scanner(System.in);
    }

    public Material addMaterial(Project project) {
        String continueChoice;
        Material material = null;

        do {
            System.out.println("--- Add Material ---");

            System.out.print("Enter the name of the material: ");
            String name = scanner.nextLine();

            System.out.print("Enter the quantity of this material: ");
            double quantity = scanner.nextDouble();

            System.out.print("Enter the unit cost of the material (€/m² or €/litre): ");
            double unitCost = scanner.nextDouble();

            System.out.print("Enter the transport cost of the material (€): ");
            double transportCost = scanner.nextDouble();

            System.out.print("Enter the quality coefficient of the material (1.0 = standard, > 1.0 = high quality): ");
            double coefficientQuality = scanner.nextDouble();

            System.out.print("Enter the VAT rate of the material: ");
            double vatRate = scanner.nextDouble();
            scanner.nextLine();

            Component component = new Component();
            component.setName(name);
            component.setComponentType(ComponentType.MATERIAL.name());
            component.setVatRate(vatRate);
            component.setProject(project);

            Component savedComponent = componentService.save(component);

            material = new Material(0, name, "Material", vatRate, project, unitCost, quantity, transportCost, coefficientQuality, savedComponent);
            materialService.save(material);
            System.out.print("Would you like to add another material? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return material;
    }


}
