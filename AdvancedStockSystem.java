package Q1;

import java.util.*;

abstract class StockItem {
    protected String itemId, itemName, category, supplier;
    protected int quantityInStock;
    protected double pricePerUnit;

    public StockItem(String itemId, String itemName, int quantityInStock, double pricePerUnit, String category, String supplier) {
        if (quantityInStock < 0) throw new IllegalArgumentException("Quantity cannot be negative.");
        if (pricePerUnit <= 0) throw new IllegalArgumentException("Price must be greater than zero.");
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantityInStock = quantityInStock;
        this.pricePerUnit = pricePerUnit;
        this.category = category;
        this.supplier = supplier;
    }

    public abstract void updateStock(int quantity);
    public abstract double calculateStockValue();
    public abstract void generateStockReport();
    public abstract boolean validateStock();
}

class ElectronicsItem extends StockItem {
    private int warrantyMonths;
    private double discount = 0;

    public ElectronicsItem(String itemId, String itemName, int quantity, double price, String supplier, int warrantyMonths) {
        super(itemId, itemName, quantity, price, "Electronics", supplier);
        if (warrantyMonths <= 0 || warrantyMonths > 60) throw new IllegalArgumentException("Invalid warranty.");
        this.warrantyMonths = warrantyMonths;
    }

    public void applyDiscount(double discountPercentage) {
        if (discountPercentage > 50) throw new IllegalArgumentException("Discount exceeds 50%");
        this.discount = pricePerUnit * (discountPercentage / 100);
        this.pricePerUnit -= discount;
    }

    @Override
    public void updateStock(int quantity) {
        this.quantityInStock += quantity;
    }

    @Override
    public double calculateStockValue() {
        return quantityInStock * pricePerUnit;
    }

    @Override
    public void generateStockReport() {
        System.out.println("\n----- Stock Report -----");
        System.out.println("Item ID: " + itemId);
        System.out.println("Item Name: " + itemName);
        System.out.println("Category: " + category);
        System.out.println("Q1.Supplier: " + supplier);
        System.out.println("Quantity In Stock: " + quantityInStock);
        System.out.println("Price Per Unit (after discount): " + pricePerUnit);
        System.out.println("Warranty Period (months): " + warrantyMonths);
        System.out.println("Total Stock Value: " + calculateStockValue());
    }

    @Override
    public boolean validateStock() {
        return quantityInStock > 0;
    }
}

class Product {
    private String productId, productName, brand, supplier;
    private int stockQuantity;

    public Product(String productId, String productName, String brand, String supplier, int stockQuantity) {
        if (productName == null || productName.isEmpty()) throw new IllegalArgumentException("Invalid product name");
        if (stockQuantity < 0) throw new IllegalArgumentException("Invalid quantity");
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.supplier = supplier;
        this.stockQuantity = stockQuantity;
    }
}

class Supplier {
    private String supplierId, companyName, contactPerson, phone, email;

    public Supplier(String supplierId, String companyName, String contactPerson, String phone, String email) {
        if (!email.contains("@")) throw new IllegalArgumentException("Invalid email");
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
    }
}

class Warehouse {
    private String warehouseId, location, managerName;
    private int capacity;

    public Warehouse(String warehouseId, String location, int capacity, String managerName) {
        this.warehouseId = warehouseId;
        this.location = location;
        this.capacity = capacity;
        this.managerName = managerName;
    }

    public void trackInventoryMovement(String itemId, int quantityMoved) {
        System.out.println("Item " + itemId + " moved: " + quantityMoved);
    }
}

public class AdvancedStockSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter Item ID: ");
            String itemId = sc.nextLine();

            System.out.print("Enter Item Name: ");
            String itemName = sc.nextLine();

            System.out.print("Enter Quantity in Stock: ");
            int quantity = sc.nextInt();

            System.out.print("Enter Price per Unit: ");
            double price = sc.nextDouble();
            sc.nextLine(); // consume newline

            System.out.print("Enter Q1.Supplier Name: ");
            String supplier = sc.nextLine();

            System.out.print("Enter Warranty Period (months): ");
            int warranty = sc.nextInt();

            ElectronicsItem ei = new ElectronicsItem(itemId, itemName, quantity, price, supplier, warranty);

            System.out.print("Enter Discount Percentage (max 50%): ");
            double discount = sc.nextDouble();
            ei.applyDiscount(discount);

            ei.generateStockReport();

            if (!ei.validateStock()) {
                System.out.println("Warning: Stock is not valid for sale.");
            } else {
                System.out.println("Stock is valid and ready for sale.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
