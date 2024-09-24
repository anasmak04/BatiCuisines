package main.java.ui;

import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.exception.MaterialNotFoundException;
import main.java.service.ComponentService;
import main.java.service.MaterialService;
import main.java.utils.MaterialHelper;

import java.util.List;
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


    public void materialMenu() {
        int choice;

        do {
            System.out.println("\n--- Material Management Menu ---");
            System.out.println("1. Add new material");
            System.out.println("2. Find all materials");
            System.out.println("3. Find material by ID");
            System.out.println("4. Update material");
            System.out.println("5. Delete material");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addMaterial(new Project());
                    break;
                case 2:
                    findAll();
                    break;
                case 3:
                    findById();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    System.out.println("Exiting material menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);
    }


    public Material addMaterial(Project project) {
        String continueChoice;
        Material material = null;

        do {

            material = MaterialHelper.getMaterialDetails(0L, project);

            Material savedMaterial = materialService.save(material);
            if (savedMaterial != null) {
                System.out.println("Material saved successfully with ID: " + savedMaterial.getId());
            } else {
                System.out.println("Error saving material.");
            }

            System.out.print("Would you like to add another material? (y/n): ");
            continueChoice = scanner.nextLine().trim().toLowerCase();

        } while (continueChoice.equals("y"));

        return material;
    }


    public void findAll() {
        System.out.println("--- List of All Materials ---");

        List<Material> materials = materialService.findAll();

        if (materials.isEmpty()) {
            System.out.println("No materials found.");
            return;
        }

        System.out.printf("| %-10s | %-20s | %-10s | %-10s | %-15s | %-20s |%n",
                "ID", "Component Name", "Unit Cost", "Quantity", "Transport Cost", "Coefficient Quality");
        System.out.println("-------------------------------------------------------------------------------");

        materials.forEach(material -> {
            System.out.printf("| %-10d | %-20s | %-10.2f | %-10.2f | %-15.2f | %-20.2f |%n",
                    material.getId(),
                    material.getName(),
                    material.getUnitCost(),
                    material.getQuantity(),
                    material.getTransportCost(),
                    material.getCoefficientQuality());
        });

        System.out.println("-------------------------------------------------------------------------------");
    }


    public void findById() {
        System.out.println("--- Find Material by Id ---");
        System.out.print("Enter the id of the material: ");
        Long id = scanner.nextLong();

        materialService.findById(id).ifPresentOrElse(material -> {
            System.out.printf("| %-15s | %-20s |%n", "Property", "Value");
            System.out.println("----------------------------------------------");
            System.out.printf("| %-15s | %-20d |%n", "ID", material.getId());
            System.out.printf("| %-15s | %-20s |%n", "Component Name", material.getName());
            System.out.printf("| %-15s | %-20.2f |%n", "Unit Cost", material.getUnitCost());
            System.out.printf("| %-15s | %-20.2f |%n", "Quantity", material.getQuantity());
            System.out.printf("| %-15s | %-20.2f |%n", "Transport Cost", material.getTransportCost());
            System.out.printf("| %-15s | %-20.2f |%n", "Coefficient Quality", material.getCoefficientQuality());
            System.out.println("----------------------------------------------");
        }, () -> {
            System.out.println("Material with id " + id + " not found.");
        });
    }


    public void update() {
        System.out.print("Enter the id of the material: ");
        Long id = scanner.nextLong();

        scanner.nextLine();

        Material material = materialService.findById(id).orElseThrow(() -> new MaterialNotFoundException("material not found"));
        Project project = new Project();

        Material material1 = MaterialHelper.getMaterialDetails(id, project);
        materialService.update(material1);
    }


    public void delete() {
        System.out.println("--- Delete Material ---");
        System.out.print("Enter the id of the material: ");
        Long id = scanner.nextLong();
        materialService.delete(id);
    }


}
