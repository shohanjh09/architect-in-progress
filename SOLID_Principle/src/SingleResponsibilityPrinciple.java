/**
 * Single Responsibility Principle (SRP)
 * A class should have only one reason to change, meaning it should have only one job or responsibility.
 */

Class Maker {
    private String name;
    private String color;
    private int year;
    private int price;

    public Maker(String name, String color, int year, int price) {
        this.name = name;
        this.color = color;
        this.year = year;
        this.price = price;
    }
}

class Invoice {
    prtivate Maker maker;
    private int quantity;

    public Invoice(Maker maker, int quantity) {
        this.maker = maker;
        this.quantity = quantity;
    }

    public int calculateTotal() {
        return maker.getPrice() * quantity;
    }

    public void printInvoice() {
        System.out.println("Invoice for " + quantity + " units of " + maker.getName());
        System.out.println("Total: $" + calculateTotal());
    }

    public void saveToDB() {
        // Code to save invoice to database
    }
}

/**
 * In this example, there are different type of responsibilities:
 * - If we need to change the way we calculate the total, we need to modify the calculateTotal method.
 * - If we need to change the way we print the invoice, we need to modify the printInvoice method.
 * - If we need to change the way we save the invoice to the database, we need to modify the saveToDB method.
 * Each method has a single responsibility, So the class has multiple reasons to change.
 * This violates the Single Responsibility Principle.
 */


/**
 * Now let's refactor the code to adhere to the Single Responsibility Principle
 */

class Invoice {
    private Maker maker;
    private int quantity;

    public Invoice(Maker maker, int quantity) {
        this.maker = maker;
        this.quantity = quantity;
    }

    public int calculateTotal() {
        return maker.getPrice() * quantity;
    }
}

/**
 * We have removed the printInvoice and saveToDB methods from the Invoice class.
 * Now, the Invoice class has only one responsibility: to represent an invoice and calculate the total.
 * This adheres to the Single Responsibility Principle.
 */

Class InvoieDao {
    private Invoice invoice;

    public InvoiceDao(Invoice invoice) {
        this.invoice = invoice;
    }

    public void saveToDB() {
        // Code to save invoice to database
    }
}

/**
 * Similarly, by separating the database functionality into a different class (InvoiceDao), we ensure that each class has a single responsibility.
 * Now, if we need to change the way we save the invoice to the database, we only need to modify the InvoiceDao class.
 * This adheres to the Single Responsibility Principle.
 */

class InvoicePrinter {
    private Invoice invoice;

    public InvoicePrinter(Invoice invoice) {
        this.invoice = invoice;
    }

    public void print() {
        System.out.println("Invoice for " + invoice.getQuantity() + " units of " + invoice.getMaker().getName());
        System.out.println("Total: $" + invoice.calculateTotal());
    }
}

/**
 * Similarly, by separating the printing functionality into a different class (InvoicePrinter), we ensure that each class has a single responsibility.
 * Now, if we need to change the way we print the invoice, we only need to modify the InvoicePrinter class.
 * This adheres to the Single Responsibility Principle.
 */