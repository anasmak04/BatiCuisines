package main.java.utils;

import main.java.domain.entities.Client;
import main.java.domain.entities.Devis;
import main.java.domain.entities.Project;
import main.java.exception.ClientNotFoundException;
import main.java.exception.DevisNotFoundException;
import main.java.exception.ProjectNotFoundException;

public class Validations {

    public static void projectValidation(Project project) {
        if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
            throw new ProjectNotFoundException("Project name cannot be null or empty");
        }
        if (project.getProfitMargin() < 0) {
            throw new ProjectNotFoundException("ProfitMargin cannot be negative");
        }
        if (project.getStatus() == null) {
            throw new ProjectNotFoundException("Status cannot be null");
        }

        if (project.getTotalCost() < 0) {
            throw new ProjectNotFoundException("Total cost cannot be negative");
        }

        if (project.getClient() == null || project.getClient().getId() <= 0) {
            throw new ProjectNotFoundException("Client cannot be null or empty");
        }
    }

    public static void clientValidation(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new ClientNotFoundException("Client name cannot be null or empty");
        }
        if (client.getAddress() == null || client.getAddress().isEmpty()) {
            throw new ClientNotFoundException("Address cannot be null or empty");
        }
        if (client.getPhone() == null || client.getPhone().isEmpty()) {
            throw new ClientNotFoundException("Phone cannot be null or empty");
        }
    }

    public static void devisValidation(Devis devis) {
        if (devis.getEstimatedAmount() > 0) {
            throw new DevisNotFoundException("Estimated amount cannot be greater than zero");
        }
        if (devis.getIssueDate() == null) {
            throw new DevisNotFoundException("IssueDate cannot be null");
        }
    }

    public static void ClientByNameValidation(String name){
        if(name == null || name.isEmpty()){
            throw new ClientNotFoundException("Client name cannot be null or empty");
        }
    }
}
