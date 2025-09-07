# 🔄 Code Reusability & Composition Over Inheritance

This document explores the principles of **Code Reusability** and **Composition Over Inheritance**, focusing on reducing duplication and building flexible, maintainable software systems.

---

## ♻️ Code Reusability

- **Definition**: Writing code in a way that allows components, functions, and modules to be reused across different parts of an application or multiple projects.  
- **Key Techniques**:
  - Create modular functions and classes.
  - Use libraries and shared packages.
  - Apply DRY (Don't Repeat Yourself) principle.
- **Benefits**:
  - Reduces redundancy.
  - Saves development time.
  - Increases consistency across systems.
- **Example**:
  ```java
  // Reusable utility method
  public class MathUtils {
      public static int gcd(int a, int b) {
          return b == 0 ? a : gcd(b, a % b);
      }
  }

  // Reuse across the application
  int result = MathUtils.gcd(20, 8);
  ```

---

## 🧩 Composition Over Inheritance

- **Definition**: A design principle that suggests **favoring composition (object collaboration)** instead of relying heavily on inheritance hierarchies.  
- **Key Idea**: Instead of extending classes, combine behaviors by including instances of other classes.  
- **Benefits**:
  - Reduces tight coupling from deep inheritance chains.
  - Promotes flexibility by mixing behaviors at runtime.
  - Avoids the “fragile base class” problem.
- **Example**:

  ✅ **Good (Composition)**:
  ```java
  class Engine {
      public void start() {
          System.out.println("Engine starting...");
      }
  }

  class Car {
      private Engine engine;

      public Car(Engine engine) {
          this.engine = engine;
      }

      public void drive() {
          engine.start();
          System.out.println("Car is driving...");
      }
  }

  // Usage
  Engine engine = new Engine();
  Car car = new Car(engine);
  car.drive();
  ```

  ❌ **Bad (Inheritance)**:
  ```java
  class Engine {
      public void start() {
          System.out.println("Engine starting...");
      }
  }

  // Inheriting when composition is better
  class Car extends Engine {
      public void drive() {
          start();
          System.out.println("Car is driving...");
      }
  }
  ```

---

## ⚖️ Trade-offs

- **Inheritance**:
  - ✅ Useful when there’s a clear “is-a” relationship.
  - ❌ Can lead to rigid and tightly coupled hierarchies.
- **Composition**:
  - ✅ More flexible with “has-a” relationships.
  - ✅ Promotes reusability and loose coupling.
  - ❌ May require more boilerplate code in some cases.

---

## 🛠️ Best Practices

1. Follow **DRY** to reduce code duplication.  
2. Use **composition** for flexibility; use **inheritance** only for strict hierarchies.  
3. Build small, reusable components and services.  
4. Apply **interfaces and abstractions** to decouple implementations.  
5. Favor **dependency injection** for modular and testable code.  

---

# 📊 Principle Summary

| Concept                   | Focus                     | Benefit                         |
|----------------------------|---------------------------|---------------------------------|
| **Code Reusability**       | Reducing duplication      | Consistency, efficiency         |
| **Composition over Inheritance** | Flexible design via object collaboration | Maintainability, loose coupling |

---

# ✅ Conclusion

- **Code Reusability** ensures that common logic is centralized and shared, avoiding duplication.  
- **Composition Over Inheritance** provides flexibility, reduces coupling, and prevents fragile hierarchies.  

Together, these principles guide developers to create **scalable, reusable, and maintainable software systems**.
