package main.java.repository.interfaces;


import main.java.domain.entities.Project;

public interface ProjectInterface extends CrudInterface<Project>{
     Project findProjectByName(String name);
     void updateProjectFields(Long projctId , double marginProfit , double totalCost);
     boolean updateProjectStatus(Long projctId, String status);
}
