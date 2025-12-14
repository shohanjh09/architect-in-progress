
# Indexing & Query Optimization Deep Dive

## What is an Index?
An index reduces full table scans.

---

## Index Types
- Clustered (Primary Key)
- Non-Clustered
- Composite

```sql
CREATE INDEX idx_user_status ON users(user_id, status);
```

Rule: Left-most column matters.

---

## When NOT to Index
- Low-cardinality columns
- Highly volatile columns

---

## Execution Plan
```sql
EXPLAIN SELECT * FROM orders WHERE user_id = 10;
```

Check:
- type
- key
- rows

---

## Common Mistakes
❌ SELECT *
❌ Functions on indexed columns

---

## Query Optimization Rules
- Fetch required columns
- Use LIMIT
- Prefer JOIN over subquery
