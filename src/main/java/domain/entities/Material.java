package main.java.domain.entities;

public class Material extends Component {
    private double unitCost;
    private double quantity;
    private double transportCost;
    private double coefficientQuality;
    private Component component;

    public Material(Long id, String name, String componentType, double vatRate, Project project, double unitCost, double quantity, double transportCost, double coefficientQuality, Component component) {
        super(id, name, componentType, vatRate, project);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.coefficientQuality = coefficientQuality;
        this.component = component;
    }

   public Material(){}

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getCoefficientQuality() {
        return coefficientQuality;
    }

    public void setCoefficientQuality(double coefficientQuality) {
        this.coefficientQuality = coefficientQuality;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
