# 🧩 Software Design Principles: YAGNI & Occam’s Razor

This document explains two guiding principles in software engineering and problem-solving: **YAGNI (You Ain’t Gonna Need It)** and **Occam’s Razor**. Both emphasize simplicity and avoiding unnecessary complexity.

---

## 🚫 YAGNI (You Ain’t Gonna Need It)

- **Definition**: A principle from **Extreme Programming (XP)** that states developers should not add functionality until it is absolutely necessary.  
- **Key Idea**: Do not build features based on speculation or assumptions about future needs.  
- **Benefits**:
  - Reduces wasted development time.
  - Keeps codebase smaller and simpler.
  - Minimizes technical debt and maintenance overhead.
- **Examples**:
  - ✅ **Good (YAGNI)**:
    ```java
    // Implement only what is needed now
    public class Invoice {
        private double amount;

        public double getAmountWithTax(double taxRate) {
            return amount + (amount * taxRate);
        }
    }
    ```
  - ❌ **Bad (Not YAGNI)**:
    ```java
    // Adding unnecessary complexity for "future use"
    public class Invoice {
        private double amount;
        private double discount; // Not required yet
        private String couponCode; // Not required yet
        private double shippingFee; // Not required yet

        public double getAmountWithTax(double taxRate) {
            return amount + (amount * taxRate);
        }
    }
    ```

---

## 🔪 Occam’s Razor

- **Definition**: A problem-solving principle that states: *Among competing solutions, the simplest one is usually the best*.  
- **Key Idea**: Avoid unnecessary assumptions and complexity when designing systems or solving problems.  
- **Benefits**:
  - Simplifies design and implementation.
  - Makes code easier to understand and maintain.
  - Reduces risk of bugs introduced by overengineering.
- **Examples**:
  - ✅ **Good (Occam’s Razor)**:
    ```java
    // Simple solution for checking if number is even
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
    ```
  - ❌ **Bad (Overcomplicated)**:
    ```java
    // Overengineered solution
    public boolean isEven(int number) {
        if (number == 0) return true;
        else if (number == 1) return false;
        else return isEven(number - 2);
    }
    ```

---

# 📊 Principle Comparison

| Principle      | Focus                                    | Outcome                           |
|----------------|------------------------------------------|-----------------------------------|
| **YAGNI**      | Avoid adding unnecessary features        | Saves time, reduces technical debt|
| **Occam’s Razor** | Prefer the simplest solution possible | Clearer, more maintainable systems|

---

# ✅ Conclusion

- **YAGNI** reminds us to implement only what is needed, avoiding speculative features.  
- **Occam’s Razor** guides us to prefer **simpler solutions** over more complex ones when both are valid.  

Together, they encourage developers to build software that is **lean, efficient, and maintainable**.
