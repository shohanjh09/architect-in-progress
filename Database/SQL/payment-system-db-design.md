# Payment System DB Design (High-Value, Production-Oriented)

> Goal: design a payment/ledger database that is **auditable**, **idempotent**, and safe under retries, concurrency, and partial failures.

---

## 1) Core Principles (Interview + Real World)

### 1.1 Never “just update balance”
For money, you want an **append-only ledger** so you can:
- Recompute balances
- Audit every change
- Detect fraud/bugs
- Support chargebacks/refunds cleanly

### 1.2 Idempotency is mandatory
Payment gateways retry. Your workers retry. Users double-click.
Your DB design must ensure **the same payment request cannot be applied twice**.

### 1.3 Double-entry accounting mindset
Each business event becomes balanced entries (debit/credit) across accounts.

---

## 2) Minimum Schema (Relational)

### 2.1 Accounts (logical buckets of money)
```sql
CREATE TABLE accounts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  owner_type VARCHAR(20) NOT NULL,   -- user, platform, vendor, escrow
  owner_id BIGINT NOT NULL,
  currency CHAR(3) NOT NULL,         -- 'USD', 'BDT'
  status TINYINT NOT NULL DEFAULT 1, -- 1 active
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_owner_currency (owner_type, owner_id, currency)
);
```

### 2.2 Ledger Entries (append-only)
Each row represents one side of an accounting movement.

```sql
CREATE TABLE ledger_entries (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  txn_id CHAR(36) NOT NULL,          -- UUID for transaction group
  account_id BIGINT NOT NULL,
  direction ENUM('DEBIT','CREDIT') NOT NULL,
  amount_cents BIGINT NOT NULL,      -- store as integer (avoid float issues)
  currency CHAR(3) NOT NULL,
  ref_type VARCHAR(30) NOT NULL,     -- order, payout, refund, chargeback
  ref_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_txn (txn_id),
  INDEX idx_account_time (account_id, created_at),
  CONSTRAINT fk_ledger_account FOREIGN KEY (account_id) REFERENCES accounts(id)
);
```

### 2.3 Payments (gateway interaction)
```sql
CREATE TABLE payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  idempotency_key VARCHAR(100) NOT NULL,
  provider VARCHAR(30) NOT NULL,     -- stripe, paypal, paddle
  provider_payment_id VARCHAR(100) NULL,
  user_id BIGINT NOT NULL,
  order_id BIGINT NULL,
  amount_cents BIGINT NOT NULL,
  currency CHAR(3) NOT NULL,
  status ENUM('INIT','AUTHORIZED','CAPTURED','FAILED','REFUNDED','CHARGEBACK') NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uq_idempotency (idempotency_key),
  UNIQUE KEY uq_provider_payment (provider, provider_payment_id),
  INDEX idx_user_time (user_id, created_at)
);
```

### 2.4 Outbox (for reliable events)
Ensures DB changes and message publication stay consistent.
```sql
CREATE TABLE outbox_events (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  aggregate_type VARCHAR(30) NOT NULL,   -- payment, order
  aggregate_id BIGINT NOT NULL,
  event_type VARCHAR(50) NOT NULL,       -- payment.captured
  payload_json JSON NOT NULL,
  status ENUM('PENDING','SENT','FAILED') NOT NULL DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_status_time (status, created_at)
);
```

---

## 3) Money Flow Examples

### 3.1 Buyer pays for an order (capture)
- Buyer account: DEBIT
- Platform escrow: CREDIT

Pseudo-transaction (single DB transaction):
```sql
START TRANSACTION;

-- 1) Ensure idempotency
INSERT INTO payments (idempotency_key, provider, user_id, order_id, amount_cents, currency, status)
VALUES ('pay_order_1001_attempt_1', 'stripe', 10, 1001, 5000, 'USD', 'CAPTURED');

-- 2) Ledger entries (same txn_id groups them)
SET @txn_id = UUID();

INSERT INTO ledger_entries (txn_id, account_id, direction, amount_cents, currency, ref_type, ref_id)
VALUES
(@txn_id, 101, 'DEBIT', 5000, 'USD', 'order', 1001),   -- buyer acct 101
(@txn_id, 201, 'CREDIT', 5000, 'USD', 'order', 1001);  -- escrow acct 201

-- 3) Outbox event for async processing
INSERT INTO outbox_events (aggregate_type, aggregate_id, event_type, payload_json)
VALUES ('payment', LAST_INSERT_ID(), 'payment.captured',
        JSON_OBJECT('order_id', 1001, 'amount_cents', 5000, 'currency', 'USD'));

COMMIT;
```

### 3.2 Refund
- Escrow: DEBIT
- Buyer: CREDIT
Keep it as a new txn (never mutate old entries).

---

## 4) Balance Calculation Options

### 4.1 Compute-on-read (pure ledger)
```sql
SELECT
  SUM(CASE WHEN direction='CREDIT' THEN amount_cents ELSE -amount_cents END) AS balance_cents
FROM ledger_entries
WHERE account_id = 201 AND currency='USD';
```
Pros: perfect audit. Cons: expensive at scale.

### 4.2 Cached balance table (recommended at scale)
Maintain `account_balances(account_id, balance_cents)` updated within same transaction.
Still keep ledger as source of truth.

---

## 5) Concurrency, Locks, and Safety

### 5.1 Avoid race conditions
If maintaining cached balances, use:
- `SELECT ... FOR UPDATE` on the balance row
- or atomic update `balance = balance + X` inside transaction

### 5.2 Deadlocks
To reduce deadlocks:
- Always lock accounts in a consistent order (e.g., by account_id ascending)
- Keep transactions short

---

## 6) Auditing and Reconciliation

### 6.1 Reconcile provider statements
Store `provider_payment_id`, amounts, fees, timestamps.
Compare gateway exports vs DB totals.

### 6.2 Immutable logs
Ledger is append-only. If a correction needed, add a correcting txn.

---

## 7) Interview Questions (What to say)
- “How do you prevent double charge?” → **Idempotency key + unique constraint + transactional write**
- “How do you ensure auditability?” → **Append-only ledger**
- “What about eventual events?” → **Outbox pattern**
- “How to scale balances?” → **Cached balances + ledger source of truth**

---

## 8) Common Pitfalls
- Using `FLOAT` for money
- Updating balances without ledger
- No idempotency keys
- Handling webhooks without state machine
- Not testing refunds/chargebacks

---

**End of file**
