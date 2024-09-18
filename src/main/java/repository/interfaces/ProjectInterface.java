package main.java.repository.interfaces;

import main.java.domain.entities.Client;
import main.java.domain.entities.Material;
import main.java.domain.entities.Project;
import main.java.domain.entities.WorkForce;

import java.util.List;
import java.util.Optional;

public interface ProjectInterface extends CrudInterface<Project>{
    @Override
    public Project save(Project entity);

    @Override
    public Optional<Project> findById(Long id);

    @Override
    public List<Project> findAll();

    @Override
    public Project update(Project entity);

    @Override
    public boolean delete(Long id);

    public boolean updateFields(Long projectId , double marginProfit , double totalCost);


     public Project findProjectByName(String name);
}
