# Indexing & Query Optimization Deep Dive

This guide explains the purpose, types, and best practices of indexing, as well as practical query optimization techniques. Each section includes explanations, trade-offs, and real-world examples.

---

## What is an Index?
An **index** is a data structure that improves the speed of data retrieval operations on a database table at the cost of additional storage and slower writes. Indexes work like a book's table of contents, allowing the database to find rows quickly without scanning the entire table.

**Example:**
- Without an index: To find all orders for user_id=10, the DB scans every row.
- With an index on user_id: The DB jumps directly to relevant rows.

---

## Index Types
- **Clustered Index**: Determines the physical order of data in the table. Each table can have only one clustered index (usually the primary key).
  - *Example*: In SQL Server, the primary key is a clustered index by default.
- **Non-Clustered Index**: A separate structure that points to the data rows. Tables can have multiple non-clustered indexes.
  - *Example*: `CREATE INDEX idx_email ON users(email);`
- **Composite Index**: An index on multiple columns. The order of columns matters (left-most column rule).
  - *Example*: `CREATE INDEX idx_user_status ON users(user_id, status);`

**Rule:** The left-most column(s) in a composite index are most important for query performance.

---

## When NOT to Index
- **Low-cardinality columns** (few unique values, e.g., gender, boolean flags)
- **Highly volatile columns** (frequently updated, e.g., last_accessed)
- **Small tables** (full scan is fast enough)

**Why?**
- Unnecessary indexes waste storage and slow down INSERT/UPDATE/DELETE operations.

---

## Execution Plan
An **execution plan** shows how the database will execute a query. Use it to identify slow operations and ensure indexes are used.

**How to check:**
```sql
EXPLAIN SELECT * FROM orders WHERE user_id = 10;
```
- Look for:
  - `type` (should be `ref` or `range`, not `ALL`)
  - `key` (which index is used)
  - `rows` (how many rows scanned)

**Example Output:**
| id | select_type | table  | type | key           | rows |
|----|-------------|--------|------|---------------|------|
| 1  | SIMPLE      | orders | ref  | idx_user_id   | 100  |

---

## Common Mistakes
- ❌ Using `SELECT *` (fetches unnecessary columns, slows down queries)
- ❌ Using functions on indexed columns (prevents index usage)
  - *Example*: `WHERE LOWER(email) = 'a@b.com'` (index on email won't be used)
- ❌ Over-indexing (too many indexes slow down writes)

---

## Query Optimization Rules
- Fetch only required columns (avoid `SELECT *`)
- Use `LIMIT` to restrict result size
- Prefer `JOIN` over subqueries for related data
- Filter on indexed columns in `WHERE`/`JOIN`/`ORDER BY`
- Avoid sorting large result sets without an index

**Example:**
```sql
-- Good: Uses index, fetches only needed columns
SELECT id, name FROM users WHERE status = 'active' LIMIT 100;

-- Bad: No index used, fetches all columns
SELECT * FROM users WHERE LOWER(email) = 'a@b.com';
```

---

## Advanced Tips
- Use **covering indexes** (index includes all columns needed by the query)
- Regularly review and drop unused indexes
- Analyze slow query logs to find optimization opportunities

**Example:**
```sql
-- Covering index for this query
CREATE INDEX idx_user_name_status ON users(name, status);
SELECT name, status FROM users WHERE status = 'active';
```

---

## Interview Summary
- Indexes speed up reads but slow down writes
- Use the right index type for your query patterns
- Always check the execution plan
- Avoid common pitfalls (over-indexing, functions on indexed columns)

---

**End of file**
