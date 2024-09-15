package main.java.utils;

import main.java.domain.entities.Project;
import main.java.exception.ProjectNotFoundException;

public class Validations {

    public static void projectValidation(Project project) {
        if(project.getProjectName() == null || project.getProjectName().isEmpty()) {
            throw new ProjectNotFoundException("Project name cannot be null or empty");
        }
        if(project.getProfitMargin() < 0){
            throw new ProjectNotFoundException("ProfitMargin cannot be negative");
        }
        if(project.getStatus() == null){
            throw new ProjectNotFoundException("Status cannot be null");
        }

        if(project.getTotalCost() < 0){
            throw new ProjectNotFoundException("Total cost cannot be negative");
        }

        if(project.getClient() == null || project.getClient().getId() <= 0){
            throw new ProjectNotFoundException("Client cannot be null or empty");
        }
    }
}
