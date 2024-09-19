package main.java.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Component {
    private Long id;
    private String name;
    private String componentType;
    private double vatRate;
    private Project project;
    private List<Material> materials = new ArrayList<>();
    private List<WorkForce> workForces = new ArrayList<>();

    public Component(Long id, String name, String componentType, double vatRate, Project project) {
        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.vatRate = vatRate;
        this.project = project;
    }

    public Component() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<WorkForce> getWorkForces() {
        return workForces;
    }

    public void setWorkForces(List<WorkForce> workForces) {
        this.workForces = workForces;
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    public void addWorkForce(WorkForce workForce) {
        this.workForces.add(workForce);
    }
}

