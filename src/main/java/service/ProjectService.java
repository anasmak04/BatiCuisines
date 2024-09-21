package main.java.service;


import main.java.domain.entities.Project;
import main.java.repository.impl.ProjectRepository;
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

    public Project findProjectByName(String name) {
       return projectRepository.findProjectByName(name);
    }

    public void updateProjectFields(Long projectId , double profitMargin , double totalCost){
        projectRepository.updateProjectFields(projectId, profitMargin , totalCost);
    }

    public boolean updateProjectStatus(Long projectId , String status){
      return projectRepository.updateProjectStatus(projectId,status);
    }

}
