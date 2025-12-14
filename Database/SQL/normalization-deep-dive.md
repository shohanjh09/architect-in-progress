# Normalization Deep Dive (1NF → 5NF)

Normalization is the process of structuring a relational database to reduce redundancy and improve data integrity. Each normal form addresses specific types of anomalies and inefficiencies. Below, each form is explained with practical examples and trade-offs.

---

## Why Normalize?
Normalization prevents:
- **Data duplication:** Same data stored in multiple places.
- **Update anomalies:** Inconsistent data after updates.
- **Delete anomalies:** Unintended data loss when deleting records.
- **Insert anomalies:** Difficulty adding new data due to missing other data.

**Example:**
If a user's email is stored in both `orders` and `users`, updating the email in one place but not the other causes inconsistency.

---

## 1NF – Atomic Values
**Rule:** Each column must contain only indivisible (atomic) values. No arrays or lists in a single field.

❌ **Bad:**
| order_id | products         |
|----------|-----------------|
| 1        | mouse,keyboard  |

✅ **Good:**
- `orders(order_id)`
- `order_items(order_id, product)`

**SQL Example:**
```sql
-- Not 1NF
orders
| order_id | products         |
|----------|-----------------|
| 1        | mouse,keyboard  |

-- 1NF
orders
| order_id |
|----------|
| 1        |

order_items
| order_id | product   |
|----------|-----------|
| 1        | mouse     |
| 1        | keyboard  |
```

---

## 2NF – No Partial Dependency
**Rule:** Every non-key attribute must depend on the whole of every candidate key (no partial dependency on a composite key).

❌ **Bad:**
- Table: `order_items(order_id, product_id, product_name)`
- `product_name` depends only on `product_id`, not the full key `(order_id, product_id)`.

✅ **Good:**
- `products(product_id, product_name)`
- `order_items(order_id, product_id)`

**SQL Example:**
```sql
-- Not 2NF
order_items
| order_id | product_id | product_name |
|----------|------------|--------------|
| 1        | 101        | mouse        |

-- 2NF
order_items
| order_id | product_id |
|----------|------------|
| 1        | 101        |

products
| product_id | product_name |
|------------|--------------|
| 101        | mouse        |
```

---

## 3NF – No Transitive Dependency
**Rule:** No non-key attribute depends on another non-key attribute (no transitive dependency).

❌ **Bad:**
- Table: `orders(order_id, user_id, user_email)`
- `user_email` depends on `user_id`, not directly on `order_id`.

✅ **Good:**
- `users(user_id, email)`
- `orders(order_id, user_id)`

**SQL Example:**
```sql
-- Not 3NF
orders
| order_id | user_id | user_email |
|----------|---------|------------|
| 1        | 10      | a@b.com    |

-- 3NF
orders
| order_id | user_id |
|----------|---------|
| 1        | 10      |

users
| user_id | email   |
|---------|---------|
| 10      | a@b.com |
```

---

## BCNF (Boyce-Codd Normal Form)
**Rule:** Every determinant is a candidate key. Handles certain edge cases not covered by 3NF, especially when there are multiple candidate keys.

**Example:**
- Table: `courses(course, instructor, room)`
- If each course is taught by one instructor, and each room is assigned to one instructor, but an instructor can teach multiple courses in different rooms, BCNF helps split the table to avoid anomalies.

---

## 4NF – No Multi-Valued Dependencies
**Rule:** No table should have two or more independent multi-valued facts about an entity.

**Example:**
- Table: `student_courses_hobbies(student_id, course, hobby)`
- If a student can have multiple courses and multiple hobbies, but courses and hobbies are independent, split into two tables:
  - `student_courses(student_id, course)`
  - `student_hobbies(student_id, hobby)`

---

## 5NF – Join Dependency
**Rule:** Decompose tables only when necessary to eliminate redundancy, but only if the original table can be reconstructed by joining the decomposed tables.

**Example:**
- Used in rare, complex cases such as when a table describes three or more many-to-many relationships that are independent.

---

## Denormalization
**Definition:** The deliberate introduction of redundancy for performance reasons, often in read-heavy systems.

**When to Use:**
- Reporting, analytics, dashboards
- When query speed is more important than strict consistency

**Trade-off:**
✔ Faster reads, simpler queries
❌ Data duplication, risk of inconsistency

**Example:**
- Store `user_email` in the `orders` table for fast reporting, even though it's also in `users`.

---

## Summary Table
| Normal Form | Prevents                | Example Fix                  |
|-------------|-------------------------|------------------------------|
| 1NF         | Non-atomic values       | Split lists into rows        |
| 2NF         | Partial dependencies    | Move attributes to new table |
| 3NF         | Transitive dependencies | Separate related tables      |
| BCNF        | Anomalous candidate keys| Further decomposition        |
| 4NF         | Multi-valued dependencies| Split into separate tables   |
| 5NF         | Join dependencies       | Advanced decomposition       |

---

**End of file**
