
# Database Engineering – Complete Interview & Reference Guide

This guide covers **SQL + NoSQL fundamentals**, database design, performance, security,
and system design concepts with **clear explanations and examples**.

---

## 1. Database Fundamentals

A database is a system for **storing, querying, updating, and securing data** efficiently.

Goals:
- Consistency
- Performance
- Reliability
- Scalability
- Security

---

## 2. Database Types

### Relational Databases (SQL)
Examples: MySQL, PostgreSQL

- Fixed schema
- ACID compliance
- Strong consistency
- Supports joins

Use cases:
- Banking
- Payments
- ERP systems

### NoSQL Databases
Examples: MongoDB, DynamoDB, Redis

- Flexible schema
- Horizontally scalable
- Optimized for specific access patterns

Use cases:
- Caching
- Event logs
- Real-time systems

---

## 3. SQL vs NoSQL

| Aspect | SQL | NoSQL |
|------|-----|------|
| Schema | Fixed | Flexible |
| Scaling | Vertical | Horizontal |
| Consistency | Strong | Eventual / Tunable |
| Joins | Supported | Limited / None |

---

## 4. ACID Properties

- Atomicity: All operations succeed or none
- Consistency: DB rules are preserved
- Isolation: Transactions don’t interfere
- Durability: Data survives crashes

Example:
```sql
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```

---

## 5. CAP Theorem

Only two of the following can be guaranteed:
- Consistency
- Availability
- Partition tolerance

Distributed DBs always choose **P** and trade off **C vs A**.

---

## 6. SQL Basics

### Common Data Types
- INT, BIGINT
- DECIMAL
- VARCHAR, TEXT
- DATE, TIMESTAMP
- JSON

Always use the **smallest valid data type**.

---

## 7. Relationships

- One-to-One (user → profile)
- One-to-Many (user → orders)
- Many-to-Many (users ↔ roles)

Example junction table:
```sql
CREATE TABLE user_roles (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY (user_id, role_id)
);
```

---

## 8. DDL & DML

DDL: CREATE, ALTER, DROP  
DML: INSERT, UPDATE, DELETE

---

## 9. Indexing (Overview)

Indexes speed up reads but slow writes.

```sql
CREATE INDEX idx_orders_user ON orders(user_id);
```

Use indexes on:
- WHERE
- JOIN
- ORDER BY

---

## 10. Transactions

Transactions group operations into a single unit.

```sql
START TRANSACTION;
UPDATE inventory SET stock = stock - 1 WHERE id = 10;
COMMIT;
```

---

## 11. Backup & Restore

- Full backups
- Incremental backups
- Binary logs

Always **test restore**.

---

## 12. Security

- Encryption at rest and in transit
- RBAC
- SQL injection prevention
- Auditing

---

## 13. Best Practices

- Proper naming conventions
- Document schemas
- Monitor performance
- Archive old data

---

END OF GUIDE
