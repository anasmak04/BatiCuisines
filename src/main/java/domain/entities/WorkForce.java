package main.java.domain.entities;

public class WorkForce {
    private double hourlyCost;
    private double workingHours;
    private double workerProductivity;

    public WorkForce(double hourlyCost, double workingHours, double workerProductivity) {
        this.hourlyCost = hourlyCost;
        this.workingHours = workingHours;
        this.workerProductivity = workerProductivity;
    }

    public WorkForce(){}

    public double getHourlyCost() {
        return hourlyCost;
    }

    public void setHourlyCost(double hourlyCost) {
        this.hourlyCost = hourlyCost;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }
}