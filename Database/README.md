# Database Folder Structure and Detailed Index

This folder contains all SQL and NoSQL database documentation, guides, and best practices. Each file covers a specific topic or deep dive. See the main README.md for a full index.

## Folder Structure

- `SQL/` – SQL database concepts, design, and engineering
    - `database-deep-dive.md`
    - `database-engineering-guide.md`
    - `normalization-deep-dive.md`
    - `indexing-and-query-optimization.md`
    - `transactions-and-ledger-design.md`
    - `high-scale-read-architecture.md`
    - `messaging-system-db-design.md`
    - `payment-system-db-design.md`
- `NoSQL/` – NoSQL database concepts, design, and engineering
    - `nosql-data-modeling-patterns.md`
    - `redis-internals-and-patterns.md`
    - `aws-database-design-guide.md`
- `General/` – General topics, interview prep, and cross-cutting concerns
    - `database-interview-questions.md`

## Detailed Table of Contents

### 1. SQL Databases (`SQL/`)
- **[database-deep-dive.md](./SQL/database-deep-dive.md)**
  - Data types, normalization (1NF, 2NF, 3NF, BCNF, 4NF, 5NF)
  - Relationships (one-to-one, one-to-many, many-to-many)
  - DDL & DML queries, data selection, sorting, limiting, filtering
  - Joins (INNER, LEFT, RIGHT, FULL, CROSS, SELF)
  - Aggregate functions, grouping, filtering
  - Data integrity & constraints (PK, FK, Index, Auto Increment, NULL, Default, Sequence)
  - Views, triggers, stored procedures, functions
  - Table variables, temp variables
  - ER diagrams, advanced SQL (subqueries, CTE, set ops, ranking, JSON handling)
  - CRUD operations, transaction & rollback, error handling, concurrency, deadlocks
  - Partitioning, sharding, full-text search, denormalization
  - Indexing (clustered, non-clustered, composite, execution plan)
  - Auditing, user/role management, deployment/versioning
  - Performance monitoring, maintenance, query optimization

- **[database-engineering-guide.md](./SQL/database-engineering-guide.md)**
  - Engineering best practices, backup/restore, optimization plans, JSON in SQL, key-value columns

- **[normalization-deep-dive.md](./SQL/normalization-deep-dive.md)**
  - Higher normalization forms (BCNF, 4NF, 5NF), denormalization strategies

- **[indexing-and-query-optimization.md](./SQL/indexing-and-query-optimization.md)**
  - Index types, composite indexes, execution plans, query optimization techniques

- **[transactions-and-ledger-design.md](./SQL/transactions-and-ledger-design.md)**
  - Transaction fundamentals, error handling, rollback, concurrency, deadlock management

- **[high-scale-read-architecture.md](./SQL/high-scale-read-architecture.md)**
  - Read replicas, caching, archiving, partitioning, sharding

- **[messaging-system-db-design.md](./SQL/messaging-system-db-design.md)**
  - Messaging system DB patterns, queue tables, outbox pattern

- **[payment-system-db-design.md](./SQL/payment-system-db-design.md)**
  - Payment system DB design, ledger, audit, reconciliation

### 2. NoSQL Databases (`NoSQL/`)
- **[nosql-data-modeling-patterns.md](./NoSQL/nosql-data-modeling-patterns.md)**
  - NoSQL types, schema design, one-to-many/many-to-many, unstructured data, best practices

- **[redis-internals-and-patterns.md](./NoSQL/redis-internals-and-patterns.md)**
  - Redis data types, partitioning, pipelining, caching, clustering

- **[aws-database-design-guide.md](./NoSQL/aws-database-design-guide.md)**
  - DynamoDB, global tables, partitioning, consistency, integration

### 3. General Topics (`General/`)
- **[database-interview-questions.md](./General/database-interview-questions.md)**
  - Interview prep, common questions, best practices
- **Data Security**: Authentication, authorization, encryption, RBAC
- **Migration Strategies**: SQL to NoSQL, backup/restore, disaster recovery
- **System Design Decisions**: Caching vs. permanent store, best practices, replication, high availability
- **Advanced Features**: Aggregation pipelines, secondary indexes, cloud integration

## How to Use
- Start with `SQL/database-deep-dive.md` for foundational SQL knowledge.
- Use topic-specific files for deep dives and advanced patterns.
- Refer to interview questions for self-assessment.

---

**For the full index, see the main README.md in the root directory.**
