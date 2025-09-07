# 🔗 Software Design Principles: Cohesion & Coupling

This document explains the principles of **Cohesion** and **Coupling**, which are crucial for designing high-quality, maintainable, and scalable software systems.

---

## 🧩 Cohesion

- **Definition**: The degree to which the responsibilities of a single module/class are related to one another.  
- **Key Idea**: A highly cohesive module has a well-defined purpose and focuses on a single responsibility.  
- **Types of Cohesion** (from worst to best):
  - Coincidental Cohesion – unrelated tasks grouped together (worst).
  - Logical Cohesion – related logically but not functionally.
  - Temporal Cohesion – tasks executed at the same time.
  - Procedural Cohesion – tasks executed in sequence.
  - Communicational Cohesion – tasks working on the same data.
  - Functional Cohesion – all elements contribute to one well-defined task (best).
- **Benefits**:
  - Easier to understand and maintain.
  - Reusable modules.
  - Simplifies debugging and testing.
- **Example**:
  - ✅ **High Cohesion**:
    ```java
    class InvoicePrinter {
        public void print(Invoice invoice) {
            // Only printing responsibility
            System.out.println(invoice.toString());
        }
    }
    ```
  - ❌ **Low Cohesion**:
    ```java
    class InvoiceManager {
        public void calculateTotal(Invoice invoice) { /* ... */ }
        public void print(Invoice invoice) { /* ... */ }
        public void saveToDB(Invoice invoice) { /* ... */ }
    }
    // Too many unrelated responsibilities
    ```

---

## 🔗 Coupling

- **Definition**: The degree of dependency between two modules/classes.  
- **Key Idea**: Low coupling means modules are independent, while high coupling indicates strong interdependence.  
- **Types of Coupling** (from worst to best):
  - Content Coupling – one module modifies another’s internal data.
  - Common Coupling – multiple modules share global data.
  - Control Coupling – one module controls another’s flow.
  - Stamp Coupling – modules share complex data structures.
  - Data Coupling – modules share simple, necessary data only (best).
- **Benefits of Low Coupling**:
  - Increases flexibility and reusability.
  - Simplifies maintenance and testing.
  - Enables parallel development.
- **Example**:
  - ✅ **Low Coupling**:
    ```java
    class PaymentService {
        private PaymentProcessor processor;

        public PaymentService(PaymentProcessor processor) {
            this.processor = processor;
        }

        public void process(double amount) {
            processor.processPayment(amount);
        }
    }

    interface PaymentProcessor {
        void processPayment(double amount);
    }

    class StripeProcessor implements PaymentProcessor {
        public void processPayment(double amount) { /* Stripe API */ }
    }
    ```
  - ❌ **High Coupling**:
    ```java
    class PaymentService {
        private StripeProcessor stripe = new StripeProcessor();

        public void process(double amount) {
            stripe.processPayment(amount); // Tightly coupled to Stripe
        }
    }
    ```

---

## ⚖️ Trade-offs

- **High Cohesion & Low Coupling** = Ideal design (maintainable, reusable, testable).  
- **High Cohesion but High Coupling** = Functional modules but too dependent.  
- **Low Cohesion but Low Coupling** = Independent but poorly organized.  
- **Low Cohesion & High Coupling** = Worst design (spaghetti code).  

---

## 🛠️ Refactoring Techniques

1. **Extract Class / Method**: Split large classes or methods into smaller, cohesive ones.  
2. **Introduce Interfaces**: Reduce coupling by depending on abstractions.  
3. **Dependency Injection**: Pass dependencies instead of hardcoding them.  
4. **Single Responsibility Principle (SRP)**: Ensure classes have one reason to change.  
5. **Layered Architecture**: Separate concerns into presentation, business, and data layers.  

---

# 📊 Principle Comparison

| Principle  | Focus                                | Ideal State                   |
|------------|--------------------------------------|-------------------------------|
| **Cohesion** | Internal consistency of a module    | High cohesion (single purpose) |
| **Coupling** | Dependency between modules          | Low coupling (minimal reliance)|

---

# ✅ Conclusion

- **High cohesion** ensures modules are focused and meaningful.  
- **Low coupling** ensures modules remain flexible and independent.  
- By applying **refactoring techniques**, developers can improve both principles, leading to **cleaner, maintainable, and scalable systems**.
