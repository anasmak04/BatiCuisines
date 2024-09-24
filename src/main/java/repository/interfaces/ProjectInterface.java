package main.java.repository.interfaces;


import main.java.domain.entities.Project;

import java.util.Optional;

public interface ProjectInterface extends CrudInterface<Project>{
     Optional<Project> findProjectByName(String name);
     void updateProjectFields(Long projctId , double marginProfit , double totalCost);
     boolean updateProjectStatus(Long projctId, String status);
}
