# 🔐 Software Design Principles: Encapsulation & Information Hiding

This document explains the closely related principles of **Encapsulation** and **Information Hiding**, which are foundational concepts in **Object-Oriented Programming (OOP)** and software design.

---

## 📦 Encapsulation

- **Definition**: The practice of bundling data (fields) and the methods that operate on that data into a single unit (class).  
- **Key Idea**: Keep related variables and functions together and restrict direct access to internal state.  
- **Benefits**:
  - Protects object integrity by preventing external interference.
  - Provides controlled access via methods (getters/setters).
  - Promotes modularity and code reusability.
- **Examples**:
  - ✅ **Good (Encapsulation)**:
    ```java
    public class BankAccount {
        private double balance;

        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        public void deposit(double amount) {
            if (amount > 0) balance += amount;
        }

        public void withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
            }
        }

        public double getBalance() {
            return balance;
        }
    }
    ```
  - ❌ **Bad (No Encapsulation)**:
    ```java
    public class BankAccount {
        public double balance; // Direct access from outside

        // No validation or protection
    }
    ```

---

## 🕵️ Information Hiding

- **Definition**: The principle of concealing implementation details of a module/class from other parts of the system, exposing only what is necessary.  
- **Key Idea**: Users of a class/module should only know *what* it does, not *how* it works internally.  
- **Benefits**:
  - Reduces system complexity.
  - Prevents external code from depending on implementation details.
  - Facilitates changes in implementation without affecting clients.
- **Examples**:
  - ✅ **Good (Information Hiding)**:
    ```java
    public class PasswordManager {
        private String hashedPassword;

        public void setPassword(String password) {
            // Hide hashing details
            this.hashedPassword = hash(password);
        }

        public boolean authenticate(String password) {
            return this.hashedPassword.equals(hash(password));
        }

        private String hash(String input) {
            // Internal implementation detail (hidden)
            return Integer.toHexString(input.hashCode());
        }
    }
    ```
  - ❌ **Bad (Implementation Exposed)**:
    ```java
    public class PasswordManager {
        public String hashedPassword; // Directly exposed
    }
    ```

---

# 📊 Principle Comparison

| Principle            | Focus                                 | Outcome                             |
|----------------------|---------------------------------------|-------------------------------------|
| **Encapsulation**    | Grouping data and behavior in one unit| Protects object integrity, reusability |
| **Information Hiding** | Concealing internal details          | Reduces complexity, improves maintainability |

---

# 🏆 Best Practices

1. Use **access modifiers** (`private`, `protected`, `public`) wisely.  
2. Provide controlled access with **getters and setters** only when necessary.  
3. Keep implementation details private; expose only required interfaces.  
4. Design classes around **what they do**, not **how they do it**.  
5. Follow the **Law of Demeter** (minimal knowledge principle).  

---

# ✅ Conclusion

- **Encapsulation** organizes and protects code by keeping data and methods together.  
- **Information Hiding** ensures that internal details remain invisible to the outside world.  

Together, these principles enhance **security, maintainability, and flexibility**, forming the backbone of robust object-oriented design.
