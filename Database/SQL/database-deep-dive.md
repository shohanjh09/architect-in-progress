# Database Deep Dive – Normalization, Indexing, Transactions & NoSQL

This document explains **WHY things are designed in a certain way**, with trade-offs and real-world examples. It is structured to help engineers understand not just the *what*, but the *why* and *how* of database design decisions.

---

## 1. Normalization

**Normalization** is the process of organizing data to minimize redundancy and dependency. It improves data integrity and makes updates safer and more efficient.

### 1NF (First Normal Form)
- **Rule:** Each column must contain only atomic (indivisible) values; no repeating groups or arrays.
- **Bad Example:**
  - `products = "mouse,keyboard"` (multiple values in one field)
- **Good Example:**
  - Use an `order_items` table with one row per product per order.
- **SQL Example:**
```sql
-- Not 1NF
orders
| id | products         |
|----|-----------------|
| 1  | mouse,keyboard  |

-- 1NF
order_items
| order_id | product   |
|----------|-----------|
| 1        | mouse     |
| 1        | keyboard  |
```

**Why?**
- Prevents anomalies when inserting, updating, or deleting data.
- Makes querying and reporting easier.

---

### 2NF (Second Normal Form)
- **Rule:** No partial dependency of any column on a composite primary key. All non-key attributes must depend on the whole key.
- **How to Achieve:**
  - Move attributes that depend only on part of the key to a separate table.
- **Example:**
  - Move product info from `order_items` to a `products` table.
- **SQL Example:**
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

**Why?**
- Prevents update anomalies and redundancy.

---

### 3NF (Third Normal Form)
- **Rule:** No transitive dependencies (non-key attributes depending on other non-key attributes).
- **Bad Example:**
  - `orders` table has `user_id` and `email` columns, but `email` depends on `user_id`.
- **Fix:**
  - Store `email` in the `users` table, not in `orders`.
- **SQL Example:**
```sql
-- Not 3NF
orders
| id | user_id | email        |
|----|---------|--------------|
| 1  | 10      | a@b.com      |

-- 3NF
orders
| id | user_id |
|----|---------|
| 1  | 10      |

users
| id | email   |
|----|---------|
| 10 | a@b.com |
```

**Why?**
- Ensures each fact is stored only once, reducing inconsistency risk.

---

### Denormalization

**Denormalization** is the process of deliberately introducing redundancy for performance reasons, usually in read-heavy systems.

- **Example:** Store `user_email` in the `orders` table for faster reads.
- **SQL Example:**
```sql
orders
| id | user_id | user_email   |
|----|---------|--------------|
| 1  | 10      | a@b.com      |
```
- **Trade-offs:**
  - **Pros:** Faster reads, simpler queries.
  - **Cons:** Manual consistency management, risk of stale data.

**When to Use:**
- When read performance is critical and data changes infrequently.

---

## 2. Indexing Deep Dive

**Indexes** speed up data retrieval at the cost of additional storage and slower writes.

### Index Types
- **Clustered Index:**
  - Determines the physical order of data in the table (usually the primary key).
  - **Example:**
    - In SQL Server, the primary key is a clustered index by default.
- **Non-clustered Index:**
  - Separate structure for fast lookups; does not affect row order.
  - **Example:**
    - `CREATE INDEX idx_email ON users(email);`
- **Composite Index:**
  - Index on multiple columns. **Rule:** The left-most column(s) are most important for query performance.
  - **Example:**
    - `CREATE INDEX idx_user_status_created ON orders(user_id, status, created_at);`

**Example Query Using Index:**
```sql
SELECT * FROM orders WHERE user_id = 10 AND status = 'PAID';
```
- This query will use the composite index above if available.

**Why?**
- Indexes make SELECT queries faster, but slow down INSERT/UPDATE/DELETE.

---

### Common Indexing Mistakes
- ❌ Over-indexing: Too many indexes slow down writes and use more storage.
- ❌ Using functions on indexed columns in WHERE clauses: Prevents index usage (e.g., `WHERE LOWER(email) = 'a@b.com'`).

**Best Practices:**
- Index columns used in WHERE, JOIN, and ORDER BY clauses.
- Regularly review and drop unused indexes.
- **Example:**
  - Remove unused index: `DROP INDEX idx_old ON orders;`

---

## 3. Transactions Deep Dive

**Transactions** ensure a sequence of operations is completed fully or not at all (ACID properties: Atomicity, Consistency, Isolation, Durability).

### Isolation Levels

| Level              | Problems Prevented         | Problems Allowed         |
|--------------------|---------------------------|-------------------------|
| Read Uncommitted   | None                      | Dirty reads             |
| Read Committed     | Dirty reads               | Non-repeatable reads    |
| Repeatable Read    | Non-repeatable reads      | Phantom reads           |
| Serializable       | Phantom reads             | None                    |

- **Dirty Read:** Reading uncommitted changes from another transaction.
- **Non-repeatable Read:** Same query returns different results within a transaction.
- **Phantom Read:** New rows added/removed by another transaction are seen in the current transaction.

**Example:**
```sql
-- Transaction 1
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
-- Transaction 2 (before commit)
SELECT balance FROM accounts WHERE id = 1; -- May see uncommitted value (dirty read)
```

**Trade-off:** Higher isolation = fewer anomalies, but lower concurrency and performance.

---

### Deadlocks

A **deadlock** occurs when two or more transactions wait for each other to release locks, causing all to hang.

**Prevention Techniques:**
- Acquire locks in a consistent order.
- Keep transactions short.
- Implement retry logic for failed transactions.

**Example:**
```sql
-- Transaction 1
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
-- Transaction 2
BEGIN;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
-- Now both try to update the other's row, causing a deadlock
```

**Why?**
- Prevents system stalls and improves reliability.

---

## 4. NoSQL Deep Dive

**NoSQL** databases are designed for scalability, flexibility, and high availability. They are often used when relational models are too rigid or slow for the use case.

### Key Concepts
- **Schema-less:** No fixed table structure; each record can have different fields.
  - **Example:**
    - MongoDB document: `{ "name": "Alice", "age": 30 }`
- **Query-first design:** Data model is based on how data will be accessed.
  - **Example:**
    - Store user and their orders together in a document for fast retrieval.
- **Horizontal scaling:** Easy to add more servers to handle more data/traffic.
  - **Example:**
    - Sharding in MongoDB or Cassandra.

---

### NoSQL Types & Examples
- **Key-Value:** Redis, DynamoDB
  - **Example:**
    - `SET user:1:name "Alice"` in Redis
- **Document:** MongoDB, CouchDB
  - **Example:**
    - `{ "_id": 1, "name": "Alice", "orders": [1001, 1002] }`
- **Column-family:** Cassandra, HBase
  - **Example:**
    - Table with dynamic columns per row: `user_id | name | email | ...`
- **Graph:** Neo4j, ArangoDB
  - **Example:**
    - Nodes: User, Product; Edges: PURCHASED

---

### DynamoDB Modeling Example

- **PK (Partition Key):** `USER#1`
- **SK (Sort Key):** `ORDER#2025-01-01`
- **Example Item:**
```json
{
  "PK": "USER#1",
  "SK": "ORDER#2025-01-01",
  "amount": 100,
  "status": "PAID"
}
```

**Design Principle:**
- Model tables for access/query patterns, not for normalization.
- Store related data together for efficient retrieval.

**Why?**
- Optimizes for performance and scalability in distributed systems.

---

### Consistency Models
- **Strong Consistency:** Reads always return the latest write.
  - **Example:**
    - DynamoDB with `ConsistentRead=true`
- **Eventual Consistency:** Reads may return stale data, but will eventually be up-to-date.
  - **Example:**
    - Default read in DynamoDB or most NoSQL systems.

**Trade-off:**
- Strong consistency = higher latency, lower availability.
- Eventual consistency = lower latency, higher availability.

---

## Interview Tip

When discussing database design in interviews, always explain:
- **Why** you chose a particular design or technology.
- The **trade-offs** involved (performance, consistency, complexity, etc.).
- The **real-world impact** of your choices (scalability, maintainability, reliability).
- **Example:**
  - "I chose denormalization for the orders table to improve read performance, accepting the risk of data inconsistency, because our workload is 95% reads."

---

**END OF DEEP DIVE**
