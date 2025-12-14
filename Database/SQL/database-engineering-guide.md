# Database Engineering – Complete Interview & Reference Guide

This guide covers **SQL + NoSQL fundamentals**, database design, performance, security, and system design concepts with **clear explanations, practical examples, and diagrams**. Use this as a reference for interviews and real-world engineering.

---

## 1. Database Fundamentals
A database is a system for **storing, querying, updating, and securing data** efficiently. The main goals are:
- **Consistency:** Data is accurate and reliable.
- **Performance:** Fast reads/writes for users and applications.
- **Reliability:** Data is safe from loss or corruption.
- **Scalability:** Can handle growth in data and users.
- **Security:** Protects data from unauthorized access.

**Diagram: Database System Overview**
```
[App/API] <--> [DB Engine] <--> [Storage]
```

---

## 2. Database Types

### Relational Databases (SQL)
- **Examples:** MySQL, PostgreSQL, Oracle
- **Features:** Fixed schema, ACID compliance, strong consistency, supports joins.
- **Use cases:** Banking, payments, ERP, inventory, HR systems.

### NoSQL Databases
- **Examples:** MongoDB, DynamoDB, Redis, Cassandra
- **Features:** Flexible schema, horizontally scalable, optimized for specific access patterns (key-value, document, column, graph).
- **Use cases:** Caching, event logs, real-time analytics, IoT, social feeds.

**Diagram: SQL vs NoSQL**
```
[SQL] <--> [Tables, Rows, Columns]
[NoSQL] <--> [Docs | Key-Value | Graph | Column]
```

---

## 3. SQL vs NoSQL
| Aspect       | SQL (Relational) | NoSQL (Non-relational) |
|-------------|------------------|------------------------|
| Schema      | Fixed            | Flexible               |
| Scaling     | Vertical         | Horizontal             |
| Consistency | Strong           | Eventual/Tunable       |
| Joins       | Supported        | Limited/None           |
| Transactions| Full ACID        | Limited/Varies         |

**Diagram: Scaling Approaches**
```
[SQL: Scale Up]
[NoSQL: Scale Out --> Shards/Replicas]
```

**Example:**
- SQL: `SELECT * FROM users WHERE email = 'a@b.com';`
- NoSQL (MongoDB): `db.users.find({ email: 'a@b.com' })`

---

## 4. ACID Properties
- **Atomicity:** All operations in a transaction succeed or none do.
- **Consistency:** Database rules (constraints, triggers) are always preserved.
- **Isolation:** Transactions do not interfere with each other.
- **Durability:** Once committed, data survives crashes.

**Diagram: ACID**
```
+-----------+-----------+-----------+-----------+
| Atomicity | Consistency | Isolation | Durability |
+-----------+-----------+-----------+-----------+
|   All-or- |   Valid    |  No cross |  Survives |
|  nothing  |  data      |  talk     |  crashes  |
+-----------+-----------+-----------+-----------+
```

**Example:**
```sql
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```
If any step fails, the transaction is rolled back.

---

## 5. CAP Theorem
In distributed systems, you can only guarantee two out of three:
- **Consistency:** Every read gets the latest write.
- **Availability:** Every request gets a response (may not be the latest data).
- **Partition Tolerance:** System works even if network splits occur.

**Diagram: CAP Triangle**
```
   Consistency
      /   \
     /     \
    /       \
   /         \
Avail.------Partition
```

**Example:**
- DynamoDB: Prioritizes Availability and Partition Tolerance (eventual consistency by default).
- MongoDB: Can be tuned for Consistency or Availability.

---

## 6. SQL Basics

### Common Data Types
- `INT`, `BIGINT` – Whole numbers
- `DECIMAL` – Precise numbers (money)
- `VARCHAR`, `TEXT` – Strings
- `DATE`, `TIMESTAMP` – Dates and times
- `JSON` – Semi-structured data

**Diagram: Table Structure**
```
[Table]
  | id | name | price | created_at
```

**Tip:** Always use the **smallest valid data type** for performance and storage efficiency.

**Example:**
```sql
CREATE TABLE products (
  id BIGINT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 7. Relationships
- **One-to-One:** Each user has one profile.
- **One-to-Many:** One user has many orders.
- **Many-to-Many:** Users can have many roles, and roles can belong to many users.

**Diagram: Relationship Types**
```
[User]--(1:1)-->[Profile]
[User]--(1:M)-->[Order]
[User]--(M:N)-->[User_Role]<--(N:M)--[Role]
```

**Example (Many-to-Many):**
```sql
CREATE TABLE user_roles (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY (user_id, role_id)
);
```

---

## 8. DDL & DML
- **DDL (Data Definition Language):** CREATE, ALTER, DROP (structure)
- **DML (Data Manipulation Language):** INSERT, UPDATE, DELETE (data)

**Diagram: DDL vs DML**
```
[DDL] --structure--> [Table]
[DML] --data-------> [Table]
```

**Example:**
```sql
-- DDL
CREATE TABLE customers (id BIGINT PRIMARY KEY, name VARCHAR(100));
ALTER TABLE customers ADD COLUMN email VARCHAR(100);

-- DML
INSERT INTO customers (id, name) VALUES (1, 'Alice');
UPDATE customers SET name = 'Bob' WHERE id = 1;
DELETE FROM customers WHERE id = 1;
```

---

## 9. Indexing (Overview)
Indexes speed up reads but slow down writes. Use indexes on columns in WHERE, JOIN, and ORDER BY clauses.

**Diagram: Index Structure**
```
[Table]
  |
  +---> [Index: B-Tree or Hash]
           |
           +---> [Pointers to rows]
```

**Example:**
```sql
CREATE INDEX idx_orders_user ON orders(user_id);
SELECT * FROM orders WHERE user_id = 10;
```

**Best Practices:**
- Index columns used in filters and joins.
- Avoid over-indexing (hurts write performance).
- Drop unused indexes.

---

## 10. Transactions
Transactions group operations into a single unit. If any step fails, all changes are rolled back.

**Diagram: Transaction Flow**
```
[Begin]--->[Step 1]--->[Step 2]--->...--->[Commit/Rollback]
```

**Example:**
```sql
START TRANSACTION;
UPDATE inventory SET stock = stock - 1 WHERE id = 10;
UPDATE orders SET status = 'SHIPPED' WHERE id = 1001;
COMMIT;
```

---

## 11. Backup & Restore
- **Full backups:** Copy the entire database.
- **Incremental backups:** Only changes since the last backup.
- **Binary logs:** Record all changes for point-in-time recovery.

**Diagram: Backup/Restore Flow**
```
[DB]--(full/incremental)-->[Backup File]--(restore)-->[DB]
```

**Best Practice:** Always **test restore** to ensure backups are valid.

**Example:**
- MySQL: `mysqldump -u user -p dbname > backup.sql`
- PostgreSQL: `pg_dump dbname > backup.sql`

---

## 12. Security
- **Encryption at rest and in transit:** Protects data from unauthorized access.
- **RBAC (Role-Based Access Control):** Restricts user permissions.
- **SQL injection prevention:** Use parameterized queries.
- **Auditing:** Track who did what and when.

**Diagram: Security Layers**
```
[App]--(auth)-->[DB]--(encryption)-->[Storage]
```

**Example:**
```sql
-- Parameterized query (prevents SQL injection)
SELECT * FROM users WHERE email = ?;
```

---

## 13. Best Practices
- Use clear, consistent naming conventions (e.g., snake_case for tables/columns).
- Document schemas and relationships (ER diagrams).
- Monitor performance (slow query logs, CPU, memory, disk I/O).
- Archive or purge old data to keep tables small and fast.
- Regularly review and optimize indexes.
- Test disaster recovery procedures.

**Diagram: Monitoring & Maintenance**
```
[DB]---> [Metrics/Logs]---> [Dashboard/Alerting]
```

**Example:**
- Use `EXPLAIN` to analyze query plans:
```sql
EXPLAIN SELECT * FROM orders WHERE user_id = 10;
```

---

**END OF GUIDE**
