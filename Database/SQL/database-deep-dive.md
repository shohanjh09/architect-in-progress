# Database Deep Dive – Normalization, Indexing, Transactions, JSON, Backup, and NoSQL

This document explains **WHY things are designed in a certain way**, with trade-offs, diagrams, and real-world examples. It is structured to help engineers understand not just the *what*, but the *why* and *how* of database design decisions. **Diagrams are included for key concepts.**

---

## 1. Database Design Principles
- **Start with requirements:** What are the main entities, relationships, and access patterns?
- **ER Diagrams:** Use Entity-Relationship diagrams to visualize tables and relationships.
- **Normalization:** Apply 1NF, 2NF, 3NF, and higher forms to reduce redundancy.
- **Denormalization:** Use for performance in read-heavy scenarios (see below).
- **Choose data types carefully:** Use smallest valid types for performance and storage.
- **Plan for growth:** Consider partitioning, sharding, and archiving strategies.

**Diagram: ERD Example**
```
[User]--(1:M)-->[Order]--(M:1)-->[Product]
```

---

## 2. Backup Plan and Restore (Full & Incremental)

### Full Backup
- **Definition:** A complete copy of the database at a point in time.
- **How to:**
  - MySQL: `mysqldump -u user -p dbname > backup.sql`
  - PostgreSQL: `pg_dump dbname > backup.sql`

### Incremental Backup
- **Definition:** Only backs up changes since the last backup (using binary logs or WAL).
- **How to:**
  - MySQL: Enable binary logging, use `mysqlbinlog` to replay changes.
  - PostgreSQL: Use WAL archiving and `pg_basebackup` for point-in-time recovery.

### Restore
- **Full restore:** Load the full backup file into a new or existing database.
- **Incremental restore:** Apply the full backup, then replay incremental logs.

**Best Practice:** Always test restores regularly to ensure backups are valid.

**Diagram: Backup/Restore Flow**
```
[DB]--(full/incremental)-->[Backup File]--(restore)-->[DB]
```

---

## 3. Backup Details in Scripts
- **Automate backups:** Use cron jobs or scheduled tasks to run backup scripts.
- **Script example (Linux):**
```bash
#!/bin/bash
DATE=$(date +%F)
mysqldump -u user -p'password' dbname > /backups/dbname_$DATE.sql
```
- **Retention policy:** Keep daily, weekly, and monthly backups as needed.
- **Monitor backup success:** Send alerts on failure.

---

## 4. Query Optimization & Index Plan

### Query Optimization Plan
- Use `EXPLAIN` to analyze query execution plans.
- Fetch only required columns (avoid `SELECT *`).
- Use proper indexes for WHERE, JOIN, and ORDER BY.
- Avoid functions on indexed columns in WHERE clauses.
- Use LIMIT for large result sets.

**Example:**
```sql
EXPLAIN SELECT id, name FROM users WHERE status = 'active' LIMIT 100;
```

### Index Plan
- Index columns used in filters, joins, and sorts.
- Use composite indexes for multi-column queries.
- Drop unused or redundant indexes.
- Monitor index usage and update as query patterns change.

**Diagram: Query Optimization Flow**
```
[Query]---> [Optimizer]---> [Plan: Index Scan/Table Scan]---> [Result]
```

### Overall Plan: Normalization & Denormalization
- Normalize for data integrity and update safety.
- Denormalize for read performance (e.g., store user_email in orders for reporting).
- Use materialized views or summary tables for heavy aggregations.

---

## 5. SQL DB with JSON Data

### Storing JSON in SQL
- Most modern RDBMS (MySQL, PostgreSQL, SQL Server) support JSON columns.
- Use JSON columns for semi-structured or flexible data (e.g., user preferences, metadata).

**Example:**
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY,
  name VARCHAR(100),
  preferences JSON
);

-- Insert JSON data
INSERT INTO users (id, name, preferences) VALUES (1, 'Alice', '{"theme": "dark", "lang": "en"}');

-- Query JSON data
SELECT * FROM users WHERE JSON_EXTRACT(preferences, '$.theme') = 'dark';
```

### When to Use JSON Columns
- When the schema is flexible or changes frequently.
- For storing metadata, settings, or logs.
- Not for core relational data (use columns for indexed/filterable fields).

**Diagram: JSON Column Usage**
```
[Table]
  | id | name | preferences (JSON)
```

---

## 6. Key-Value Patterns in SQL (JSON Columns)

- Use JSON columns to store key-value pairs for flexible attributes.
- **Example:**
```sql
CREATE TABLE product_attributes (
  product_id BIGINT PRIMARY KEY,
  attributes JSON
);

-- Insert key-value data
INSERT INTO product_attributes VALUES (101, '{"color": "red", "size": "M"}');

-- Query for a specific key
SELECT * FROM product_attributes WHERE JSON_EXTRACT(attributes, '$.color') = 'red';
```
- **Trade-offs:**
  - Pros: Flexibility, easy to add new keys.
  - Cons: Harder to index and query at scale; not suitable for high-performance analytics.

**Diagram: Key-Value in JSON**
```
[product_id] | { "color": "red", "size": "M" }
```

---

## 7. Normalization, Denormalization, and Indexing (Summary)
- Normalize for data integrity and update safety.
- Denormalize for performance in read-heavy scenarios.
- Use indexes to speed up queries, but avoid over-indexing.
- Use JSON columns for flexible, non-core data.
- Always have a backup and restore plan, and test it regularly.

**Diagram: Normalization vs Denormalization**
```
[Normalized: Many tables, less redundancy]
[Denormalized: Fewer tables, more redundancy]
```

---

**END OF DEEP DIVE**
