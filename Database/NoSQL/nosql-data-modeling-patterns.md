
# NoSQL Data Modeling Patterns

## Rule #1: Query First
You design schema based on access patterns.

---

## One-to-Many
Embed when data is small.

---

## Many-to-Many
Use references or duplication.

---

## Time-Series
Bucket data by time.

---

## DynamoDB Example
PK = USER#1
SK = ORDER#2025-01-01

---

## Hot Partition Problem
Occurs when partition key has skewed access.

Fix:
- Add random suffix
- Time-based partitioning
