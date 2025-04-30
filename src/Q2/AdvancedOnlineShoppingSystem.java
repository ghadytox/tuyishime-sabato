package Q2;

import java.util.*;

abstract class ShoppingItem {
    protected String itemId, itemName, itemDescription;
    protected double price;
    protected int stockAvailable;

    public ShoppingItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable) {
        if (price <= 0) throw new IllegalArgumentException("Price must be greater than 0");
        if (stockAvailable < 0) throw new IllegalArgumentException("Stock cannot be negative");

        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.stockAvailable = stockAvailable;
    }

    public abstract void updateStock(int quantity);
    public abstract void addToCart(Customer customer, ShoppingCart cart, int quantity);
    public abstract void generateInvoice(Customer customer);
    public abstract boolean validateItem();
}

// ---------------- Concrete Class ----------------
class ElectronicsItem extends ShoppingItem {
    private int warrantyMonths;

    public ElectronicsItem(String itemId, String itemName, String itemDescription, double price, int stockAvailable, int warrantyMonths) {
        super(itemId, itemName, itemDescription, price, stockAvailable);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public void updateStock(int quantity) {
        if (stockAvailable + quantity < 0) {
            System.out.println("Insufficient stock to reduce.");
        } else {
            stockAvailable += quantity;
        }
    }

    @Override
    public void addToCart(Customer customer, ShoppingCart cart, int quantity) {
        if (quantity <= stockAvailable) {
            cart.addItem(this, quantity);
            stockAvailable -= quantity;
        } else {
            System.out.println("Not enough stock available.");
        }
    }

    @Override
    public void generateInvoice(Customer customer) {
        System.out.println("Invoice for: " + customer.getCustomerName());
        System.out.println("Item: " + itemName + " | Quantity: 1 | Price: $" + price);
        System.out.println("Warranty: " + warrantyMonths + " months");
    }

    @Override
    public boolean validateItem() {
        return stockAvailable > 0;
    }

    @Override
    public String toString() {
        return itemName + " (" + itemDescription + ") - $" + price + " | Stock: " + stockAvailable;
    }
}

// ---------------- Encapsulated Classes ----------------
class Customer {
    private String customerId, customerName, email, address, phone;

    public Customer(String customerId, String customerName, String email, String address, String phone) {
        if (!email.contains("@") || address.isEmpty() || phone.length() < 10) {
            throw new IllegalArgumentException("Invalid customer details");
        }
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + customerName + ", Email: " + email +
                ", Address: " + address + ", Phone: " + phone;
    }
}

class ShoppingCart {
    private String cartId;
    private Map<ShoppingItem, Integer> cartItems;
    private double totalPrice;

    public ShoppingCart(String cartId) {
        this.cartId = cartId;
        this.cartItems = new HashMap<>();
        this.totalPrice = 0.0;
    }

    public void addItem(ShoppingItem item, int quantity) {
        cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
        totalPrice += item.price * quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Shopping Cart ID: " + cartId + "\nItems:\n");
        for (Map.Entry<ShoppingItem, Integer> entry : cartItems.entrySet()) {
            sb.append(" - ").append(entry.getKey().itemName)
                    .append(" x ").append(entry.getValue())
                    .append(" = $").append(entry.getKey().price * entry.getValue())
                    .append("\n");
        }
        sb.append("Total Price: $").append(totalPrice);
        return sb.toString();
    }
}

class Payment {
    private String paymentId, paymentMethod;
    private double amountPaid;
    private Date transactionDate;

    public Payment(String paymentId, String paymentMethod, double amountPaid) {
        if (!(paymentMethod.equalsIgnoreCase("Credit Card") || paymentMethod.equalsIgnoreCase("PayPal"))) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.transactionDate = new Date();
    }

    public String toString() {
        return "Payment ID: " + paymentId + ", Method: " + paymentMethod + ", Amount Paid: $" +
                amountPaid + ", Date: " + transactionDate.toString();
    }
}

// ---------------- Main Program ----------------
public class AdvancedOnlineShoppingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Customer Input
        System.out.println("Enter Customer Details:");
        System.out.print("Customer ID: ");
        String customerId = sc.nextLine();
        System.out.print("Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        Customer customer = new Customer(customerId, name, email, address, phone);

        // Item Input
        System.out.println("\nEnter Electronics Item Details:");
        System.out.print("Item ID: ");
        String itemId = sc.nextLine();
        System.out.print("Item Name: ");
        String itemName = sc.nextLine();
        System.out.print("Item Description: ");
        String desc = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble();
        System.out.print("Stock Available: ");
        int stock = sc.nextInt();
        System.out.print("Warranty (months): ");
        int warranty = sc.nextInt();
        ElectronicsItem ei = new ElectronicsItem(itemId, itemName, desc, price, stock, warranty);

        // Add to Cart
        System.out.print("\nEnter quantity to add to cart: ");
        int quantity = sc.nextInt();
        ShoppingCart cart = new ShoppingCart("CART1001");
        ei.addToCart(customer, cart, quantity);

        // Payment
        sc.nextLine(); // consume leftover newline
        System.out.print("Enter Payment ID: ");
        String paymentId = sc.nextLine();
        System.out.print("Enter Payment Method (Credit Card / PayPal): ");
        String method = sc.nextLine();
        Payment payment = new Payment(paymentId, method, cart.getTotalPrice());

        // Display all information
        System.out.println("\n----- Customer Info -----");
        System.out.println(customer);
        System.out.println("\n----- Item Info -----");
        System.out.println(ei);
        System.out.println("\n----- Shopping Cart -----");
        System.out.println(cart);
        System.out.println("\n----- Payment Info -----");
        System.out.println(payment);
        System.out.println("\n----- Invoice -----");
        ei.generateInvoice(customer);

        sc.close();
    }
}

