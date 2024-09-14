package main.java.domain.entities;

import java.time.LocalDate;

public class Devis {
    private int id;
    private double estimatedAmount;
    private LocalDate issueDate;
    private boolean isAccepted;
    private Project project;

    public Devis(int id, double estimatedAmount, LocalDate issueDate, boolean isAccepted, Project project) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.isAccepted = isAccepted;
        this.project = project;
    }

    public Devis() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", estimatedAmount=" + estimatedAmount +
                ", issueDate=" + issueDate +
                ", isAccepted=" + isAccepted +
                ", project=" + project +
                '}';
    }
}
