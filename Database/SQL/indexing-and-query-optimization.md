# Indexing & Query Optimization Deep Dive

This guide explains the purpose, types, and best practices of indexing, as well as practical query optimization techniques. Each section includes explanations, trade-offs, real-world examples, and diagrams for visual understanding.

---

## What is an Index?
An **index** is a data structure that improves the speed of data retrieval operations on a database table at the cost of additional storage and slower writes. Indexes work like a book's table of contents, allowing the database to find rows quickly without scanning the entire table.

**Diagram: Index Structure**
```
[Table]
  |
  +---> [Index: B-Tree or Hash]
           |
           +---> [Pointers to rows]
```

**Example:**
- Without an index: To find all orders for user_id=10, the DB scans every row.
- With an index on user_id: The DB jumps directly to relevant rows.

---

## Index Types
- **Clustered Index**: Determines the physical order of data in the table. Each table can have only one clustered index (usually the primary key).
- **Non-Clustered Index**: A separate structure that points to the data rows. Tables can have multiple non-clustered indexes.
- **Composite Index**: An index on multiple columns. The order of columns matters (left-most column rule).

**Diagram: Clustered vs Non-Clustered Index**
```
[Table Rows: physically ordered by PK] <--- Clustered Index
[Non-Clustered Index] --pointers--> [Table Rows]
```

**Rule:** The left-most column(s) in a composite index are most important for query performance.

---

## When NOT to Index
- **Low-cardinality columns** (few unique values, e.g., gender, boolean flags)
- **Highly volatile columns** (frequently updated, e.g., last_accessed)
- **Small tables** (full scan is fast enough)

**Diagram: Over-Indexing Pitfall**
```
[Table] + [Too many indexes] --> [Slow writes, wasted space]
```

---

## Execution Plan
An **execution plan** shows how the database will execute a query. Use it to identify slow operations and ensure indexes are used.

**Diagram: Query Execution Plan**
```
[Query] --> [Optimizer] --> [Plan: Index Scan, Table Scan, Join, etc.]
```

**How to check:**
```sql
EXPLAIN SELECT * FROM orders WHERE user_id = 10;
```
- Look for:
  - `type` (should be `ref` or `range`, not `ALL`)
  - `key` (which index is used)
  - `rows` (how many rows scanned)

---

## Common Mistakes
- ❌ Using `SELECT *` (fetches unnecessary columns, slows down queries)
- ❌ Using functions on indexed columns (prevents index usage)
- ❌ Over-indexing (too many indexes slow down writes)

**Diagram: Index Not Used**
```
WHERE LOWER(email) = 'a@b.com'  -->  [Index on email NOT used]
```

---

## Query Optimization Rules
- Fetch only required columns (avoid `SELECT *`)
- Use `LIMIT` to restrict result size
- Prefer `JOIN` over subqueries for related data
- Filter on indexed columns in `WHERE`/`JOIN`/`ORDER BY`
- Avoid sorting large result sets without an index

**Diagram: Covering Index**
```
[Index: (name, status)]
Query: SELECT name, status FROM users WHERE status = 'active';
-- All columns in index, no table lookup needed
```

---

## Advanced Tips
- Use **covering indexes** (index includes all columns needed by the query)
- Regularly review and drop unused indexes
- Analyze slow query logs to find optimization opportunities

**Diagram: Index Maintenance**
```
[Unused Index] --(DROP)--> [Faster writes]
```

---

## Interview Summary
- Indexes speed up reads but slow down writes
- Use the right index type for your query patterns
- Always check the execution plan
- Avoid common pitfalls (over-indexing, functions on indexed columns)

---

**End of file**
