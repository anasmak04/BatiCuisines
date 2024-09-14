package main.java.repository;

import main.java.domain.entities.Project;
import main.java.repository.interfaces.ProjectInterface;

import java.util.List;
import java.util.Optional;

public class ProjectRepository implements ProjectInterface<Project> {
    @Override
    public Project save(Project entity) {
        return null;
    }

    @Override
    public Optional<Project> findById(Project project) {
        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {
        return List.of();
    }

    @Override
    public Project update(Project entity) {
        return null;
    }

    @Override
    public boolean delete(Project entity) {
        return false;
    }
}
