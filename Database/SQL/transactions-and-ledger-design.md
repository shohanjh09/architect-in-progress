# Transactions & Ledger Design (Deep Dive)

This guide explains database transactions, isolation, deadlocks, and the ledger pattern for financial systems. Each section includes clear explanations, trade-offs, practical SQL examples, and diagrams for visual understanding.

---

## 1. What is a Transaction?
A **transaction** is an atomic unit of work in a database. It ensures that a group of operations are completed together or not at all (all-or-nothing).

**ACID Properties:**
- **Atomicity:** All operations succeed or none do.
- **Consistency:** Data remains valid before and after.
- **Isolation:** Concurrent transactions do not interfere.
- **Durability:** Once committed, changes persist even after crashes.

**Diagram: ACID Properties**
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
START TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```
If any step fails, the transaction is rolled back.

---

## 2. Isolation Levels
Isolation levels control how/when changes made by one transaction become visible to others. Higher isolation prevents more anomalies but can reduce concurrency.

| Level              | Prevents             | Allows                |
|--------------------|---------------------|-----------------------|
| Read Uncommitted   | None                | Dirty reads           |
| Read Committed     | Dirty reads         | Non-repeatable reads  |
| Repeatable Read    | Non-repeatable reads| Phantom reads         |
| Serializable       | Phantom reads       | None                  |

**Diagram: Isolation Levels**
```
Read Uncommitted < Read Committed < Repeatable Read < Serializable
      ^                 ^                ^                ^
  Most concurrent   ...   ...      Most isolated
```

**Example:**
- **Dirty Read:** Transaction A reads uncommitted changes from B.
- **Non-repeatable Read:** Same query returns different results in one transaction.
- **Phantom Read:** New rows added/removed by another transaction are seen.

**SQL Example:**
```sql
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
```

---

## 3. Deadlocks
A **deadlock** occurs when two or more transactions wait for each other to release locks, causing all to hang.

**Diagram: Deadlock**
```
Transaction 1: locks A, waits for B
Transaction 2: locks B, waits for A
   [A] <---waits--- [B]
   [B] <---waits--- [A]
```

**Prevention:**
- Always lock resources in the same order.
- Keep transactions short.
- Use retry logic for failed transactions.

**SQL Example:**
```sql
-- Transaction 1
START TRANSACTION;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
-- Transaction 2
START TRANSACTION;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
-- Both now try to update the other's row, causing a deadlock
```

---

## 4. Ledger Pattern (for Financial Systems)
Never update balances directly. Use an **append-only ledger** to record all money movement. This enables full auditability and prevents lost updates.

**Diagram: Ledger Pattern**
```
+-----------+-----------+-----------+
| account   | debit     | credit    |
+-----------+-----------+-----------+
| user      | 100       | 0         |
| merchant  | 0         | 100       |
+-----------+-----------+-----------+
```

**Ledger Table Example:**
```sql
CREATE TABLE ledger (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_id BIGINT NOT NULL,
  debit BIGINT NOT NULL DEFAULT 0,
  credit BIGINT NOT NULL DEFAULT 0,
  balance BIGINT NOT NULL, -- optional, can be computed
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Balance Calculation:**
```sql
SELECT SUM(credit - debit) AS balance FROM ledger WHERE account_id = 1;
```

**Example Entry:**
- User sends $100: DEBIT user, CREDIT merchant.

---

## 5. Idempotency
Idempotency ensures that retrying the same operation (e.g., payment) does not result in duplicate effects. This is critical for financial systems where network retries or user double-clicks are common.

**Diagram: Idempotency**
```
[Request] --idempotency_key--> [DB]
   |                              |
   +---(retry, same key)--------->|
   |<---(same result, no double effect)
```

**How to Implement:**
- Use an `idempotency_key` (unique per request) and enforce a unique constraint.

**Example:**
```sql
CREATE TABLE payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  idempotency_key VARCHAR(100) NOT NULL,
  ...
  UNIQUE KEY uq_idempotency (idempotency_key)
);

-- On retry, insert will fail or return the same result.
```

---

## 6. Best Practices & Interview Tips
- Always use transactions for multi-step updates.
- Use the highest isolation level needed for correctness.
- Use append-only ledgers for money.
- Implement idempotency for all payment/transfer endpoints.
- Monitor for deadlocks and handle retries gracefully.

---

**End of file**
