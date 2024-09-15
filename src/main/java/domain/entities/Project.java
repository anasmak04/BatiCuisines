package main.java.domain.entities;

import main.java.domain.enums.ProjectStatus;

import java.util.ArrayList;
import java.util.List;


public class Project {
    private int id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus status;
    private Client client;
    List<Component> components;
    public Project(int id, String projectName, double profitMargin, double totalCost, String status, Client client) {
        this.id = id;
        this.projectName = projectName;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.components = new ArrayList<>();
        this.status = ProjectStatus.valueOf(status);
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
