package main.java.service;

import main.java.domain.entities.Client;
import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;
import main.java.repository.*;
import main.java.utils.Validations;

import java.util.List;
import java.util.Optional;

public class ProjectService  {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project save(Project project) {
        Validations.projectValidation(project);
        return this.projectRepository.save(project);
    }

    public boolean delete(Long id) {
        return projectRepository.delete(id);
    }

    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Optional<Project> findById(Long id) {
        return this.projectRepository.findById(id);
    }

//    public void saveProjectWithDetails(Client client, Project project, Material material, WorkForce workForce) {
//        this.projectRepository.saveProjectWithDetails(client, project,material, workForce);
//    }

}
