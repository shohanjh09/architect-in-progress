# Database Folder Structure and Detailed Index

This folder contains all SQL and NoSQL database documentation, guides, and best practices. Each file covers a specific topic or deep dive. See the main README.md for a full index.

## Folder Structure

- `SQL/` – SQL database concepts, design, and engineering
    - `database-deep-dive.md` – Core concepts, normalization, indexing, transactions, JSON, backup, and NoSQL
    - `database-engineering-guide.md` – Engineering best practices, backup/restore, optimization, JSON in SQL, key-value columns
    - `normalization-deep-dive.md` – Higher normalization forms (BCNF, 4NF, 5NF), denormalization strategies
    - `indexing-and-query-optimization.md` – Index types, composite indexes, execution plans, query optimization techniques
    - `transactions-and-ledger-design.md` – Transaction fundamentals, error handling, rollback, concurrency, deadlock management, ledger pattern
    - `high-scale-read-architecture.md` – Read replicas, caching, archiving, partitioning, sharding
    - `messaging-system-db-design.md` – Messaging system DB patterns, queue tables, outbox pattern
    - `payment-system-db-design.md` – Payment system DB design, ledger, audit, reconciliation

<!-- Add NoSQL and General folders here if/when files exist -->

## Detailed Table of Contents

### 1. SQL Databases (`SQL/`)
- **[database-deep-dive.md](./SQL/database-deep-dive.md)**
  - Database design, ER diagrams, normalization (1NF, 2NF, 3NF, BCNF, 4NF, 5NF)
  - Relationships (one-to-one, one-to-many, many-to-many)
  - DDL & DML queries, data selection, sorting, limiting, filtering
  - Joins (INNER, LEFT, RIGHT, FULL, CROSS, SELF)
  - Aggregate functions, grouping, filtering
  - Data integrity & constraints (PK, FK, Index, Auto Increment, NULL, Default, Sequence)
  - Views, triggers, stored procedures, functions
  - Table variables, temp variables
  - Advanced SQL (subqueries, CTE, set ops, ranking, JSON handling)
  - CRUD operations, transaction & rollback, error handling, concurrency, deadlocks
  - Partitioning, sharding, full-text search, denormalization
  - Indexing (clustered, non-clustered, composite, execution plan)
  - Auditing, user/role management, deployment/versioning
  - Performance monitoring, maintenance, query optimization
  - Backup/restore (full & incremental), backup scripting, restore testing
  - JSON columns, key-value patterns, semi-structured data

- **[database-engineering-guide.md](./SQL/database-engineering-guide.md)**
  - Engineering best practices, backup/restore, optimization plans, JSON in SQL, key-value columns, security, monitoring

- **[normalization-deep-dive.md](./SQL/normalization-deep-dive.md)**
  - Higher normalization forms (BCNF, 4NF, 5NF), denormalization strategies, practical examples

- **[indexing-and-query-optimization.md](./SQL/indexing-and-query-optimization.md)**
  - Index types, composite indexes, execution plans, query optimization techniques, best practices

- **[transactions-and-ledger-design.md](./SQL/transactions-and-ledger-design.md)**
  - Transaction fundamentals, error handling, rollback, concurrency, deadlock management, ledger pattern, idempotency

- **[high-scale-read-architecture.md](./SQL/high-scale-read-architecture.md)**
  - Read replicas, caching, archiving, partitioning, sharding, materialized views, pagination, observability

- **[messaging-system-db-design.md](./SQL/messaging-system-db-design.md)**
  - Messaging system DB patterns, queue tables, outbox pattern, scaling, read receipts, moderation, search

- **[payment-system-db-design.md](./SQL/payment-system-db-design.md)**
  - Payment system DB design, ledger, audit, reconciliation, idempotency, concurrency, auditing, best practices

## How to Use
- Start with `SQL/database-deep-dive.md` for foundational SQL knowledge and broad coverage.
- Use topic-specific files for deep dives and advanced patterns.
- Refer to these files for interview prep, onboarding, and production reference.

---

**For the full index, see the main README.md in the root directory.**
