package main.java.domain.entities;

import main.java.domain.enums.ProjectStatus;

import java.util.ArrayList;
import java.util.List;


public class Project {
    private int id;
    private String nameProject;
    private double profitMargin;
    private double totalCost;
    List<Component> components;
    private ProjectStatus status;
    private Client client;

    public Project(int id, String nameProject, double profitMargin, double totalCost,String status,Client client) {
        this.id = id;
        this.nameProject = nameProject;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.components = components;
        components=new ArrayList<>();
        this.status=ProjectStatus.valueOf(status);
        this.client=client;

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

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
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

    public List<Component> getcomponents() {
        return components;
    }

    public void setcomponents(List<Component> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", nameProject='" + nameProject + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                '}';
    }
}
