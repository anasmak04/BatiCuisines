package main.java.domain.entities;

public class Component {
    private int id;
    private String name;
    private String componentType;
    private double vatRate;

    public Component(int id, String name, String componentType, double vatRate) {
        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.vatRate = vatRate;
    }

    public Component() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
