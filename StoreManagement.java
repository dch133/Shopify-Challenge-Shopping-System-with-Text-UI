package challenge;

/**
 *
 * @author Daniel
 */
import java.util.*;

public class StoreManagement
{

    static Shop store = new Shop();
    static Product dairyProducts = new Product("Dairy", 9.99);
    static Product meatProducts = new Product("Meats", 19.99);
    static Scanner sc = new Scanner(System.in);

    //create data for database and add items to database 
    private static void generateProducts()
    {
        //depending on the products, the item gets a predetermined price
        LineItem dairyLI1 = new LineItem("Carton of Milk", 5, dairyProducts.price);
        LineItem dairyLI2 = new LineItem("Yogurt 8-Pack", 5, dairyProducts.price);
        LineItem meatLI1 = new LineItem("Chicken Breasts", 5, meatProducts.price);
        LineItem meatLI2 = new LineItem("Fresh Steak", 5, meatProducts.price);
        dairyProducts.lineItems.add(dairyLI1);
        dairyProducts.lineItems.add(dairyLI2);
        meatProducts.lineItems.add(meatLI1);
        meatProducts.lineItems.add(meatLI2);
    }

    // Add dairy or meat items for a specific order and update the sum
    private static void addItemsToOrder(Product type, Order order, boolean remove)
    {
        do
        {
            System.out.print("Please choose from the following Products:\n"
                    + "1: " + type.lineItems.get(0).getName() + "\n"
                    + "2: " + type.lineItems.get(1).getName() + "\n"
                    + "3: Finish checking out these items\n");
            System.out.println();

            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 3)
                break;
            if (choice == 1 || choice == 2 )
            {
                int currentQuantity = type.lineItems.get(choice - 1).getQuantity();
                if (currentQuantity == 0)
                    System.out.println("No Items left in store! "
                            + "Please try again later. Sorry for the inconvience.");
                else
                {
                    order.lineItems.add(type.lineItems.get(choice - 1));
                    type.lineItems.get(choice - 1).setQuantity(currentQuantity - 1);
                    order.sum = order.sum+type.lineItems.get(choice - 1).getPrice();
                }
            }
        } while (true);
    }

    //Create or Modify an order
    private static void createOrder(int idNumber, boolean removeItems)
    {
        Order order = new Order();
        boolean creatingOrder = false;
        boolean finishOrder = false;
        if (idNumber == -1)
        {
            creatingOrder = true;
            idNumber = store.orders.size();
            System.out.println("Your unique Order ID is : " + idNumber);
        }
        while (!finishOrder)
        {
            System.out.print("Please choose products for your Order:\n"
                    + "1: Dairy\n"
                    + "2: Meat\n"
                    + "3: Finish changing current Order\n");
            System.out.println();
            switch (sc.nextLine())
            {
                case "1":
                    if (creatingOrder)
                        addItemsToOrder(dairyProducts, order, removeItems);
                    else
                        addItemsToOrder(dairyProducts, store.orders.get(idNumber), removeItems);
                    break;

                case "2":
                    if (creatingOrder)
                        addItemsToOrder(meatProducts, order, removeItems);
                    else
                        addItemsToOrder(meatProducts, store.orders.get(idNumber), removeItems);
                    break;

                case "3":
                    finishOrder = true;
                    break;
                default:
                    break;
            }
        }
        if (creatingOrder)
        {
            store.orders.add(order);
            System.out.println("Order created!");
        } else
            System.out.println("Order Modified!");

        System.out.println();

    }

    public static void main(String[] args)
    {
        try
        {
        //Fill the store with Food
        if (store.products.isEmpty())
        {
            generateProducts();
            store.products.add(meatProducts);
            store.products.add(dairyProducts);
        }
        mainMenu();
        } catch (Exception e)
        {
            System.out.println("There was an ERROR processing your request.");
            System.out.println("Your data might be invalid.");
            System.out.println();
            mainMenu();
        }

    }

    public static void mainMenu()
    {
        String input;
        do
        {
            System.out.println(
                    "Welcome to to the Fake Store!\n"
                    + "Enter one of the following commands:\n"
                    + "1: View an existing Order\n"
                    + "2: Add a new Order\n"
                    + "3: Remove an existing Order\n"
                    + "4: Change an existing Order\n"
                    + "5: View Product List\n"
                    + "6: Quit\n"
            );
            input = sc.nextLine();
        } while (!input.equals("1") && !input.equals("2") && !input.equals("3")
                && !input.equals("4") && !input.equals("5") && !input.equals("6"));

        if (input.equals("1"))
        {
            if (store.orders.isEmpty())
                System.out.print("No orders available. Make one first.");
            else
            {
                System.out.print("Please enter the Order Number: ");
                int orderNumber = Integer.parseInt(sc.nextLine());
                Order currentOrder = store.orders.get(orderNumber);
                //Print order selected
                System.out.println("Order: #" + orderNumber + "----------------");
                for (int i = 0; i < currentOrder.lineItems.size(); i++)
                {
                    System.out.println("Item Name: " + currentOrder.lineItems.get(i).getName());
                    System.out.println("Item Price: " + currentOrder.lineItems.get(i).getPrice());
                    System.out.println();
                }
                System.out.println("Total Sum: " + currentOrder.sum+" $");
                System.out.println("----------------------------------------");
            }
            System.out.println();
            mainMenu();
        }
        if (input.equals("2"))
        {
            createOrder(-1, false);
            mainMenu();
        }
        if (input.equals("3"))
        {
            if (store.orders.isEmpty())
                System.out.println("No orders available. Make one first.\n");
            else
            {
                System.out.print("Please enter the Order Number: ");
                int orderNumber = Integer.parseInt(sc.nextLine());
                if (orderNumber > store.orders.size()-1)
                    System.out.println("No such order exists! Please try again!\n");
                else
                {
                    store.orders.remove(orderNumber);
                    System.out.println("Order removed!");
                }
            }
            System.out.println();
            mainMenu();
        }

        if (input.equals("4"))
        {
            if (store.orders.isEmpty())
                System.out.print("No orders available. Make one first.\n");
            else
            {
                System.out.print("Please enter the Order Number: ");
                int orderNumber = Integer.parseInt(sc.nextLine());
                if (orderNumber > store.orders.size()-1)
                    System.out.println("No such order exists! Please try again!\n");
                else
                {
                    System.out.println("Do you want to: \n"
                            + "1: add items to order\n"
                            + "2: remove items from order");
                    String ans = sc.nextLine();
                    if(ans.equals("1"))
                        createOrder(orderNumber, false);
                    else if(ans.equals("2"))
                        System.out.println("Please delete this Order and make a new one!");
                    else System.out.println("Wrong input. Going back to main menu.\n");

                }
            }
            System.out.println();
            mainMenu();
        }

        if (input.equals("5"))
        {
            for (int i = 0; i < store.products.size(); i++)
            {
                System.out.println("Product Name: " + store.products.get(i).name);
                Product current = store.products.get(i);
                for (int j = 0; j < current.lineItems.size(); j++)
                {
                    System.out.println("Item Name: " + current.lineItems.get(j).getName());
                    System.out.println("Item Price: " + current.lineItems.get(j).getPrice());
                    System.out.println("Quantity Remaining: " + current.lineItems.get(j).getQuantity());
                    System.out.println();
                }
                System.out.println("-------------------");
            }
            System.out.println();
            mainMenu();

        }

        if (input.equals("6"))
        {
            sc.close();
            System.exit(0);
        }
    }
}
