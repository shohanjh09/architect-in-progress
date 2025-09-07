# ❌ Software Anti-Patterns

This document explains common **software anti-patterns** that reduce code quality, maintainability, and scalability. Recognizing these helps developers avoid poor design choices and refactor towards better solutions.

---

## 🏢 God/Manager Class
- **Definition**: A single class takes on too many responsibilities, becoming overly large and complex.  
- **Problems**:
  - Violates **Single Responsibility Principle (SRP)**.  
  - Hard to maintain and test.  
- **Solution**:
  - Break into smaller, cohesive classes.  
  - Apply **Separation of Concerns**.  

---

## 🔂 Singleton Overuse
- **Definition**: Excessive reliance on the Singleton pattern to manage shared state or global access.  
- **Problems**:
  - Leads to hidden dependencies.  
  - Makes testing difficult.  
  - Breaks modularity.  
- **Solution**:
  - Use **dependency injection**.  
  - Limit Singleton usage to truly unique resources (e.g., configuration, logging).  

---

## ⚙️ Overengineering
- **Definition**: Adding unnecessary complexity, abstractions, or features "just in case."  
- **Problems**:
  - Increases development time and maintenance burden.  
  - Introduces unused code.  
- **Solution**:
  - Follow **YAGNI (You Ain’t Gonna Need It)**.  
  - Implement only what is required.  

---

## 🚨 Programming by Exception
- **Definition**: Using exceptions to control normal program flow.  
- **Problems**:
  - Makes logic harder to follow.  
  - Reduces performance.  
- **Solution**:
  - Use exceptions only for exceptional cases.  
  - Apply proper conditional checks.  

---

## 👻 Poltergeists
- **Definition**: Classes created only to pass data or invoke methods on other objects, without meaningful behavior.  
- **Problems**:
  - Adds unnecessary indirection.  
  - Wastes resources and complicates design.  
- **Solution**:
  - Remove and replace with direct calls or integrate logic into meaningful classes.  

---

## ⚓ Boat Anchor
- **Definition**: Retaining unused code, components, or frameworks in the system “just in case.”  
- **Problems**:
  - Increases complexity.  
  - Confuses developers.  
- **Solution**:
  - Regularly remove unused or legacy code.  
  - Use version control for historical access.  

---

## 🧩 Interface Bloat
- **Definition**: Creating overly large interfaces with too many methods.  
- **Problems**:
  - Forces classes to implement methods they don’t need.  
  - Violates **Interface Segregation Principle (ISP)**.  
- **Solution**:
  - Break down large interfaces into smaller, role-specific ones.  

---

## 🔨 Golden Hammer
- **Definition**: Overusing a familiar tool, library, or design pattern for every problem.  
- **Problems**:
  - Ignores better solutions.  
  - Leads to suboptimal design.  
- **Solution**:
  - Evaluate tools/patterns based on context.  
  - Apply the **right tool for the job**.  

---

## ⚡ Premature Optimization
- **Definition**: Optimizing code before identifying actual performance bottlenecks.  
- **Problems**:
  - Wastes time and effort.  
  - Makes code complex and unreadable.  
- **Solution**:
  - First, write clean, correct code.  
  - Optimize only after profiling reveals bottlenecks.  

---

# 📊 Summary Table

| Anti-Pattern          | Problem                              | Solution                              |
|-----------------------|--------------------------------------|---------------------------------------|
| God/Manager Class     | Too many responsibilities            | Split into smaller, cohesive classes  |
| Singleton Overuse     | Hidden dependencies, hard to test    | Use DI, limit Singleton usage         |
| Overengineering       | Unnecessary complexity               | Apply YAGNI                           |
| Programming by Exception | Exceptions for normal flow       | Use proper conditionals               |
| Poltergeists          | Useless classes                     | Remove or merge into meaningful code  |
| Boat Anchor           | Unused code retained                 | Remove unused components              |
| Interface Bloat       | Oversized interfaces                 | Break into smaller interfaces         |
| Golden Hammer         | Overuse of one tool/pattern          | Choose tools contextually             |
| Premature Optimization| Complex code without need            | Optimize after profiling              |

---

# ✅ Conclusion

Avoiding these anti-patterns helps maintain **clean, scalable, and maintainable software**.  
By applying principles like **SOLID, YAGNI, and DRY**, teams can refactor poor designs into robust and efficient solutions.
