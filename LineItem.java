
package challenge;

/**
 * @author Daniel
 */

public class LineItem {

    private String name;
    private int quantity;
    private double price;
    
    public LineItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getName() {return this.name;}
    public int getQuantity() {return this.quantity;}
    public double getPrice() {return this.price;}
    
    public void setName(String name) {this.name = name; }
    public void setQuantity(int quant) {this.quantity = quant;}
    public void setPrice(double price) {this.price = price;}

}
