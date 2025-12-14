
# Transactions & Ledger Design

## What is a Transaction?
Atomic unit of work.

---

## Isolation Levels
- Read Uncommitted
- Read Committed
- Repeatable Read
- Serializable

---

## Deadlocks
Occurs when two transactions wait on each other.

Prevention:
- Same lock order
- Short transactions

---

## Ledger Pattern
Never UPDATE balance directly.

```text
ledger(id, account_id, debit, credit, balance)
```

Balance = SUM(credit - debit)

---

## Idempotency
Critical for payments & retries.
