# 🧪 Test-Driven Development (TDD) & Behavior-Driven Development (BDD)

This document explains the principles of **Test-Driven Development (TDD)** and **Behavior-Driven Development (BDD)**, two modern software development methodologies that emphasize testing and quality assurance throughout the development lifecycle.

---

## 🔄 Test-Driven Development (TDD)

- **Definition**: A software development approach where tests are written **before** writing the actual implementation code.  
- **Key Cycle**: *Red → Green → Refactor*  
  1. **Red**: Write a failing test.  
  2. **Green**: Write the minimal code to pass the test.  
  3. **Refactor**: Improve the code while keeping tests passing.  

- **Benefits**:
  - Ensures high test coverage.  
  - Encourages modular, loosely coupled code.  
  - Reduces bugs and improves maintainability.  

- **Example (TDD in Java)**:
  ```java
  // Step 1: Write a failing test
  @Test
  public void testAdd() {
      Calculator calc = new Calculator();
      assertEquals(5, calc.add(2, 3));
  }

  // Step 2: Write minimal code to pass the test
  class Calculator {
      public int add(int a, int b) {
          return a + b;
      }
  }
  ```

---

## 🎭 Behavior-Driven Development (BDD)

- **Definition**: An evolution of TDD that focuses on the **behavior of the system** from the user’s perspective.  
- **Key Idea**: Describe tests in a **natural language** style that stakeholders can understand.  
- **Structure**: *Given → When → Then*  
  - **Given** some initial context.  
  - **When** an action occurs.  
  - **Then** verify the outcome.  

- **Benefits**:
  - Bridges the gap between developers, testers, and business stakeholders.  
  - Improves collaboration with non-technical team members.  
  - Provides living documentation of requirements.  

- **Example (BDD with Cucumber & Gherkin)**:
  ```gherkin
  Feature: Addition
    Scenario: Add two numbers
      Given I have a calculator
      When I add 2 and 3
      Then the result should be 5
  ```

  ```java
  // Step Definitions in Java
  Calculator calc;
  int result;

  @Given("I have a calculator")
  public void i_have_a_calculator() {
      calc = new Calculator();
  }

  @When("I add {int} and {int}")
  public void i_add_numbers(int a, int b) {
      result = calc.add(a, b);
  }

  @Then("the result should be {int}")
  public void the_result_should_be(int expected) {
      assertEquals(expected, result);
  }
  ```

---

## 📊 TDD vs BDD

| Aspect        | TDD                               | BDD                                         |
|---------------|-----------------------------------|---------------------------------------------|
| Focus         | Code correctness                  | System behavior from user perspective       |
| Language      | Unit test frameworks (JUnit, NUnit, etc.) | Natural language (Gherkin, Cucumber, SpecFlow) |
| Stakeholders  | Developers, testers               | Developers, testers, business stakeholders  |
| Workflow      | Write tests before code           | Write scenarios before implementation       |

---

## 🏆 Best Practices

1. Keep tests **small, independent, and fast**.  
2. Use TDD for **unit-level testing**.  
3. Use BDD for **acceptance and integration testing**.  
4. Collaborate with business analysts and QA for BDD scenarios.  
5. Continuously run tests in CI/CD pipelines.  

---

# ✅ Conclusion

- **TDD** ensures robust, bug-resistant code by enforcing test-first development.  
- **BDD** extends TDD by focusing on **behavior and collaboration**, making requirements understandable to all stakeholders.  

Together, TDD and BDD foster **high-quality, maintainable, and user-focused software development**.
