package main.java.domain.entities;

import java.time.LocalDate;

public class Devis {
    private Long id;
    private double estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validatedDate;
    private boolean isAccepted;
    private Project project;

    public Devis(Long id, double estimatedAmount, LocalDate issueDate, LocalDate validatedDate, boolean isAccepted, Project project) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validatedDate = validatedDate;
        this.isAccepted = isAccepted;
        this.project = project;
    }

    public Devis(Long id) {
        this.id = id;
    }

    public Devis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(LocalDate validatedDate) {
        this.validatedDate = validatedDate;
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
