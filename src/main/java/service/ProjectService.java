package main.java.service;

import main.java.domain.entities.Project;
import main.java.repository.ProjectRepository;
import main.java.utils.Validations;

import java.util.List;
import java.util.Optional;

public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public Project save(Project project) {
        Validations.projectValidation(project);
        return this.projectRepository.save(project);
    }

    public Boolean delete(Project project) {
        return projectRepository.delete(project);
    }

    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Optional<Project> findById(Project project) {
        return this.projectRepository.findById(project);
    }

}
