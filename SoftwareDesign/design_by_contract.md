# 📐 Design by Contract (DbC)

This document explains the principle of **Design by Contract (DbC)**, a software correctness methodology introduced by Bertrand Meyer in the context of the Eiffel programming language. DbC helps ensure reliable software by clearly defining the obligations and benefits of software components.

---

## 📖 Definition

- **Design by Contract (DbC)** is a methodology where software components communicate based on **contracts**.  
- A **contract** specifies the rights and obligations of a software component (class, method, or module) to ensure correct behavior.  

---

## 📜 Core Elements of DbC

1. **Preconditions**  
   - Conditions that must be true before a method or function executes.  
   - Responsibility of the *caller*.  
   - Example:
     ```java
     // Method to calculate square root
     public double sqrt(double x) {
         if (x < 0) throw new IllegalArgumentException("x must be >= 0");
         return Math.sqrt(x);
     }
     // Precondition: x >= 0
     ```

2. **Postconditions**  
   - Conditions that must be true after a method or function executes.  
   - Responsibility of the *callee*.  
   - Example:
     ```java
     public int increment(int x) {
         int result = x + 1;
         assert result > x; // Postcondition
         return result;
     }
     ```

3. **Invariants**  
   - Conditions that must always hold true for a class during its lifetime.  
   - Example:
     ```java
     public class BankAccount {
         private double balance;

         public void deposit(double amount) {
             balance += amount;
             assert balance >= 0; // Invariant
         }

         public void withdraw(double amount) {
             if (amount <= balance) balance -= amount;
             assert balance >= 0; // Invariant
         }
     }
     ```

---

## 🏆 Benefits of DbC

- **Correctness**: Defines precise conditions for software behavior.  
- **Clarity**: Makes contracts explicit, reducing ambiguity.  
- **Debugging Aid**: Violations of contracts quickly expose bugs.  
- **Robustness**: Encourages defensive programming through assertions.  
- **Documentation**: Contracts serve as formal documentation for methods and classes.  

---

## ⚖️ Trade-offs

- Requires additional effort to write contracts.  
- Can add runtime overhead when using assertions.  
- May not prevent all errors (e.g., logic flaws).  
- Best when combined with testing and static analysis.  

---

## 🛠️ Best Practices

1. Define clear **preconditions** and validate them early.  
2. Ensure **postconditions** reflect intended outcomes.  
3. Maintain class **invariants** rigorously.  
4. Use **assertions** or **annotations** to enforce contracts.  
5. Combine DbC with **unit tests** for comprehensive correctness.  

---

# ✅ Conclusion

**Design by Contract (DbC)** formalizes software correctness by establishing mutual obligations:  
- **Caller** ensures preconditions.  
- **Callee** guarantees postconditions and maintains invariants.  

This approach leads to **reliable, maintainable, and self-documenting software systems**, making DbC a valuable tool in modern software design.
