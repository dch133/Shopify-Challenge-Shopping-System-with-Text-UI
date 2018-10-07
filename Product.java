
package challenge;

/**
 * @author Daniel
 */
import java.util.*;

public class Product {
    
    public ArrayList<LineItem> lineItems = new ArrayList<>();
    
    public double price;
    
    public String name;
    
    public Product (String name, double price)
    {
        this.price = price;
        this.name = name;
    }


}
