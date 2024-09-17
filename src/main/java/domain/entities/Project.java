package main.java.domain.entities;

import main.java.domain.enums.ProjectStatus;

import java.util.ArrayList;
import java.util.List;


public class Project {
    private Long id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus status;
    private double surface;
    private Client client;
    List<Component> components;

    public Project(Long id, String projectName, double profitMargin, double totalCost, String status, double surface, Client client) {
        this.id = id;
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.components = new ArrayList<>();
        this.status = ProjectStatus.valueOf(status);
        this.surface = surface;
        this.client = client;

    }

    public Project() {

    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public double getSurface() {

        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public void addComponent(Component component){
        components.add(component);
    }

    public void removeComponent(Component component){
        components.remove(component);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                '}';
    }
}
