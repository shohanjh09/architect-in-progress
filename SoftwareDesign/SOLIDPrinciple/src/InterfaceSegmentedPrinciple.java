/**
 * Interface Segmented Principle
 * Interface should be such, that client should not implement unnecessary functions
 * they do not need.
 */

interface ResturantEmployee {
    void washDishes();
    void serveCustomer();
    void cookFood();
}

class Waiter implements ResturantEmployee {
    public void washDishes() {
        // Not needed for waiter
    }

    public void serveCustomer() {
        System.out.println("Serving the customer");
    }

    public void cookFood() {
        // Not needed for waiter
    }
}

/**
 * In this example, the waiter class is forced to implement methods that it does not need. For example the washDishes and cookFood methods.
 * So this violates the Interface Segregation Principle.
 */

/**
 * Now let's refactor the code to adhere to the Interface Segregation Principle
 */

interface WaiterInterface {
    void serveCustomer();
    takeOrder();
}

interface ChefInterface {
    void cookFood();
    void decideMenu();
}

class Waiter implements WaiterInterface {
    public void serveCustomer() {
        System.out.println("Serving the customer");
    }

    public void takeOrder() {
        System.out.println("Taking order from the customer");
    }
}

/**
 * Now, the Waiter class only implements the methods that it needs. This adheres to the Interface Segregation Principle.
 */