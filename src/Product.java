import java.io.Serializable;

public class Product implements Serializable {
    String name;
    String desription;
    String ID;
    double cost;


    public Product(String name, String desription, String ID, double cost) {
        this.name = name;
        this.desription = desription;
        this.ID = ID;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public String getID() {
        return ID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product ID: " + ID + "\nName: " + name + "\nDescription: " + desription + "\nCost: " + cost;
    }
}
